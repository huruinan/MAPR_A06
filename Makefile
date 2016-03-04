YourBucketName = delayprediction2
BUCKET_PATH_JAR = s3://$(YourBucketName)/FlightPrediction.jar
BUCKET_PATH_INPUT = s3://flightprice/testDataA06/
BUCKET_PATH_OUTPUT = s3://$(YourBucketName)/output
CLUSTER_ID = j-1G84M070XJ2P4

LOCAL_NAME=A06

JCC = javac
JFLAGS = -g -classpath ./libs/*:`yarn classpath`:./target/
SRC = ./src/
TARGET = -d ./target/

StopH:
	stop-dfs.sh --config `pwd`/conf/
	stop-yarn.sh --config `pwd`/conf/
	mr-jobhistory-daemon.sh stop historyserver
	jps
StartH:
	start-dfs.sh --config `pwd`/conf/
	start-yarn.sh --config `pwd`/conf/
	mr-jobhistory-daemon.sh start historyserver
	jps
Prepare:
	hdfs dfs -rm -R -f hdfs://localhost/$(LOCAL_NAME)/
	hdfs dfs -mkdir hdfs://localhost/$(LOCAL_NAME)/
	hdfs dfs -mkdir hdfs://localhost/$(LOCAL_NAME)/data/
	hdfs dfs -copyFromLocal ../TestCases/all/101.csv.gz  hdfs://localhost/$(LOCAL_NAME)/data/
Format:
	hdfs namenode -format
Run:
	hadoop dfs -rm -R -f hdfs://localhost/$(LOCAL_NAME)/output
	export R_HOME=a && hadoop --config ./conf jar ./target/FlightPrediction.jar FlightPrediction /$(LOCAL_NAME)/data/ /$(LOCAL_NAME)/output
	#hadoop --config ./conf jar ./target/FlightPrice.jar FlightPrice /A04/data/55.csv.gz /A04/output
	rm -Rf output
	hadoop dfs -copyToLocal hdfs://localhost/$(LOCAL_NAME)/output ./

Cluster:
	
	aws s3 mb s3://$(YourBucketName)
	aws s3 cp ./src/emR_bootstrap.sh s3://$(YourBucketName)/A06_NR/
	aws s3 cp ./src/hdfs_permission.sh s3://$(YourBucketName)/A06_NR/ 
	aws emr create-cluster --release-label emr-4.3.0  --service-role EMR_DefaultRole --ec2-attributes '{"InstanceProfile":"EMR_EC2_DefaultRole","KeyName":"MAPR"}' \
	--instance-groups InstanceGroupType=MASTER,InstanceCount=1,InstanceType=m3.xlarge \
	 InstanceGroupType=CORE,InstanceCount=2,InstanceType=m3.xlarge \
	--bootstrap-actions Path=s3://$(YourBucketName)/A06_NR/emR_bootstrap.sh,Name=CustomAction,Args=[--rhdfs] \
	--steps Name=HDFS_tmp_permission,Jar=s3://elasticmapreduce/libs/script-runner/script-runner.jar,Args=s3://$(YourBucketName)/A06_NR/hdfs_permission.sh \
	--region us-east-1  --no-auto-terminate --name A06 \
	--log-uri 's3://$(YourBucketName)/elasticmapreduce/'
Step:
	aws s3 cp ./target/FlightPrediction.jar s3://$(YourBucketName)/
	cd ./bin && ./Step.sh $(CLUSTER_ID) $(BUCKET_PATH_JAR) $(BUCKET_PATH_INPUT) $(BUCKET_PATH_OUTPUT)
	rm -Rf aws_output
	aws s3 cp s3://$(YourBucketName)/output/ ./aws_output/ --recursive

Build:  FlightPrediction.class
	cd target && jar -cvf FlightPrediction.jar *.class
	cd target && jar -uvf FlightPrediction.jar -C ../libs/ com
	cd target && jar -uvf FlightPrediction.jar -C ../libs/ org

FlightPriceParser.class: $(SRC)FlightPriceParser.java
	$(JCC) $(JFLAGS) $(TARGET) $(SRC)$(<F)

FlightPredictionMapper.class: $(SRC)FlightPredictionMapper.java FlightPriceParser.class
	$(JCC) $(JFLAGS) $(TARGET) $(SRC)$(<F)

FlightPredictionReducer.class: $(SRC)FlightPredictionReducer.java
	$(JCC) $(JFLAGS) $(TARGET) $(SRC)$(<F)

FlightPrediction.class: $(SRC)FlightPrediction.java FlightPredictionMapper.class FlightPredictionReducer.class
	$(JCC) $(JFLAGS) $(TARGET) $(SRC)$(<F)

Clean:
	aws s3 rb s3://$(YourBucketName) --force
