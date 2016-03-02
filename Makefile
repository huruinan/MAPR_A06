YourBucketName = delayprediction

Cluster:
	
	aws s3 mb s3://$(YourBucketName)
	aws s3 cp ./src/emR_bootstrap.sh s3://$(YourBucketName)/A06_NR/
	aws s3 cp ./src/hdfs_permission.sh s3://$(YourBucketName)/A06_NR/ 
	aws emr create-cluster --release-label emr-4.3.0  --service-role EMR_DefaultRole --ec2-attributes InstanceProfile=EMR_EC2_DefaultRole --instance-groups InstanceGroupType=MASTER,InstanceCount=1,InstanceType=m3.xlarge InstanceGroupType=CORE,InstanceCount=5,InstanceType=m3.xlarge \
	--bootstrap-actions Path=s3://$(YourBucketName)/A06_NR/emR_bootstrap.sh,Name=CustomAction,Args=[--rstudio,--rexamples,--plyrmr,--rhdfs] \
	--steps Name=HDFS_tmp_permission,Jar=s3://elasticmapreduce/libs/script-runner/script-runner.jar,Args=s3://$(YourBucketName)/A06_NR/hdfs_permission.sh \
	--region us-east-1  --no-auto-terminate --name A06

Clean:
	aws s3 rb s3://$(YourBucketName) --force
