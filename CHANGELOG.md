 #### 👋 _Looking for changelogs for older versions? You can find them in the [changelogs](./changelogs) directory._
# __2.25.68__ __2024-06-06__
## __AWS Account__
  - ### Features
    - This release adds 3 new APIs (AcceptPrimaryEmailUpdate, GetPrimaryEmail, and StartPrimaryEmailUpdate) used to centrally manage the root user email address of member accounts within an AWS organization.

## __AWS Glue__
  - ### Features
    - This release adds support for creating and updating Glue Data Catalog Views.

## __AWS IoT Wireless__
  - ### Features
    - Adds support for wireless device to be in Conflict FUOTA Device Status due to a FUOTA Task, so it couldn't be attached to a new one.

## __AWS Storage Gateway__
  - ### Features
    - Adds SoftwareUpdatePreferences to DescribeMaintenanceStartTime and UpdateMaintenanceStartTime, a structure which contains AutomaticUpdatePolicy.

## __Amazon FSx__
  - ### Features
    - This release adds support to increase metadata performance on FSx for Lustre file systems beyond the default level provisioned when a file system is created. This can be done by specifying MetadataConfiguration during the creation of Persistent_2 file systems or by updating it on demand.

## __Amazon Kinesis Firehose__
  - ### Features
    - Adds integration with Secrets Manager for Redshift, Splunk, HttpEndpoint, and Snowflake destinations

## __Amazon Location Service__
  - ### Features
    - Added two new APIs, VerifyDevicePosition and ForecastGeofenceEvents. Added support for putting larger geofences up to 100,000 vertices with Geobuf fields.

## __Amazon S3__
  - ### Features
    - Allow user to configure content type for BlockingInputStreamAsyncRequestBody
        - Contributed by: [@chaykin](https://github.com/chaykin)

## __Amazon Simple Notification Service__
  - ### Features
    - Doc-only update for SNS. These changes include customer-reported issues and TXC3 updates.

## __Amazon Simple Queue Service__
  - ### Features
    - Doc only updates for SQS. These updates include customer-reported issues and TCX3 modifications.

## __Contributors__
Special thanks to the following contributors to this release: 

[@chaykin](https://github.com/chaykin)
# __2.25.67__ __2024-06-05__
## __AWS Global Accelerator__
  - ### Features
    - This release contains a new optional ip-addresses input field for the update accelerator and update custom routing accelerator apis. This input enables consumers to replace IPv4 addresses on existing accelerators with addresses provided in the input.

## __AWS Glue__
  - ### Features
    - AWS Glue now supports native SaaS connectivity: Salesforce connector available now

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Simple Storage Service__
  - ### Features
    - Added new params copySource and key to copyObject API for supporting S3 Access Grants plugin. These changes will not change any of the existing S3 API functionality.

# __2.25.66__ __2024-06-04__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - U7i instances with up to 32 TiB of DDR5 memory and 896 vCPUs are now available. C7i-flex instances are launched and are lower-priced variants of the Amazon EC2 C7i instances that offer a baseline level of CPU performance with the ability to scale up to the full compute performance 95% of the time.

## __Amazon EventBridge Pipes__
  - ### Features
    - This release adds Timestream for LiveAnalytics as a supported target in EventBridge Pipes

## __Amazon SageMaker Service__
  - ### Features
    - Extend DescribeClusterNode response with private DNS hostname and IP address, and placement information about availability zone and availability zone ID.

## __Tax Settings__
  - ### Features
    - Initial release of AWS Tax Settings API

# __2.25.65__ __2024-06-03__
## __AWS Amplify__
  - ### Features
    - This doc-only update identifies fields that are specific to Gen 1 and Gen 2 applications.

## __AWS Batch__
  - ### Features
    - This release adds support for the AWS Batch GetJobQueueSnapshot API operation.

## __AWS IoT TwinMaker__
  - ### Features
    - Support RESET_VALUE UpdateType for PropertyUpdates to reset property value to default or null

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Elastic Kubernetes Service__
  - ### Features
    - Adds support for EKS add-ons pod identity associations integration

# __2.25.64__ __2024-05-31__
## __AWS CodeBuild__
  - ### Features
    - AWS CodeBuild now supports Self-hosted GitHub Actions runners for Github Enterprise

## __AWS Launch Wizard__
  - ### Features
    - This release adds support for describing workload deployment specifications, deploying additional workload types, and managing tags for Launch Wizard resources with API operations.

## __Amazon CodeGuru Security__
  - ### Features
    - This release includes minor model updates and documentation updates.

## __Amazon ElastiCache__
  - ### Features
    - Update to attributes of TestFailover and minor revisions.

# __2.25.63__ __2024-05-30__
## __AWS Certificate Manager__
  - ### Features
    - add v2 smoke tests and smithy smokeTests trait for SDK testing.

## __AWS CloudTrail__
  - ### Features
    - CloudTrail Lake returns PartitionKeys in the GetEventDataStore API response. Events are grouped into partitions based on these keys for better query performance. For example, the calendarday key groups events by day, while combining the calendarday key with the hour key groups them by day and hour.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Agents for Amazon Bedrock__
  - ### Features
    - With this release, Knowledge bases for Bedrock adds support for Titan Text Embedding v2.

## __Amazon Bedrock Runtime__
  - ### Features
    - This release adds Converse and ConverseStream APIs to Bedrock Runtime

## __Amazon Connect Service__
  - ### Features
    - Adding associatedQueueIds as a SearchCriteria and response field to the SearchRoutingProfiles API

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for Aurora Postgres DBname.

## __Amazon SageMaker Service__
  - ### Features
    - Adds Model Card information as a new component to Model Package. Autopilot launches algorithm selection for TimeSeries modality to generate AutoML candidates per algorithm.

## __EMR Serverless__
  - ### Features
    - The release adds support for spark structured streaming.

# __2.25.62__ __2024-05-29__
## __AWS CodeBuild__
  - ### Features
    - AWS CodeBuild now supports manually creating GitHub webhooks

## __AWS Glue__
  - ### Features
    - Add optional field JobMode to CreateJob and UpdateJob APIs.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS SecurityHub__
  - ### Features
    - Add ROOT type for TargetType model

## __Amazon Athena__
  - ### Features
    - Throwing validation errors on CreateNotebook with Name containing `/`,`:`,`\`

## __Amazon Connect Service__
  - ### Features
    - This release includes changes to DescribeContact API's response by including ConnectedToSystemTimestamp, RoutingCriteria, Customer, Campaign, AnsweringMachineDetectionStatus, CustomerVoiceActivity, QualityMetrics, DisconnectDetails, and SegmentAttributes information from a contact in Amazon Connect.

## __Amazon S3__
  - ### Bugfixes
    - Remove CopySourceInterceptor and add the updation of "copySource" parameter in CopyObject and UploadPartCopy API via preClientExecutionRequestCustomizer Customization before client execution.

# __2.25.61__ __2024-05-28__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon DynamoDB__
  - ### Features
    - Doc-only update for DynamoDB. Specified the IAM actions needed to authorize a user to create a table with a resource-based policy.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Providing support to accept BgpAsnExtended attribute

## __Amazon S3__
  - ### Bugfixes
    - Fixed NullPointerException in S3 thrown when null metadata value is provided.

## __Amazon Simple Workflow Service__
  - ### Features
    - This release adds new APIs for deleting activity type and workflow type resources.

## __Managed Streaming for Kafka__
  - ### Features
    - Adds ControllerNodeInfo in ListNodes response to support Raft mode for MSK

# __2.25.60__ __2024-05-24__
## __AWS IoT FleetWise__
  - ### Features
    - AWS IoT FleetWise now supports listing vehicles with attributes filter, ListVehicles API is updated to support additional attributes filter.

## __Amazon DynamoDB__
  - ### Features
    - Documentation only updates for DynamoDB.

## __Amazon Managed Blockchain__
  - ### Features
    - This is a minor documentation update to address the impact of the shut down of the Goerli and Polygon networks.

# __2.25.59__ __2024-05-23__
## __AWS OpsWorks__
  - ### Features
    - Documentation-only update for OpsWorks Stacks.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __EMR Serverless__
  - ### Features
    - This release adds the capability to run interactive workloads using Apache Livy Endpoint.

# __2.25.58__ __2024-05-22__
## __AWS Chatbot__
  - ### Features
    - This change adds support for tagging Chatbot configurations.

## __AWS CloudFormation__
  - ### Features
    - Added DeletionMode FORCE_DELETE_STACK for deleting a stack that is stuck in DELETE_FAILED state due to resource deletion failure.

## __AWS Key Management Service__
  - ### Features
    - This release includes feature to import customer's asymmetric (RSA, ECC and SM2) and HMAC keys into KMS in China.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS WAFV2__
  - ### Features
    - You can now use Security Lake to collect web ACL traffic data.

## __Amazon OpenSearch Service__
  - ### Features
    - This release adds support for enabling or disabling a data source configured as part of Zero-ETL integration with Amazon S3, by setting its status.

# __2.25.57__ __2024-05-21__
## __AWS Glue__
  - ### Features
    - Add Maintenance window to CreateJob and UpdateJob APIs and JobRun response. Add a new Job Run State for EXPIRED.

## __AWS Performance Insights__
  - ### Features
    - Performance Insights added a new input parameter called AuthorizedActions to support the fine-grained access feature. Performance Insights also restricted the acceptable input characters.

## __AWS Storage Gateway__
  - ### Features
    - Added new SMBSecurityStrategy enum named MandatoryEncryptionNoAes128, new mode enforces encryption and disables AES 128-bit algorithums.

## __Amazon CloudFront__
  - ### Features
    - Model update; no change to SDK functionality.

## __Amazon Lightsail__
  - ### Features
    - This release adds support for Amazon Lightsail instances to switch between dual-stack or IPv4 only and IPv6-only public IP address types.

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for Db2 license through AWS Marketplace.

## __MailManager__
  - ### Features
    - This release includes a new Amazon SES feature called Mail Manager, which is a set of email gateway capabilities designed to help customers strengthen their organization's email infrastructure, simplify email workflow management, and streamline email compliance control.

# __2.25.56__ __2024-05-20__
## __AWS Control Tower__
  - ### Features
    - Added ListControlOperations API and filtering support for ListEnabledControls API. Updates also includes added metadata for enabled controls and control operations.

## __AWS SDK for Java v2__
  - ### Features
    - Add the reason for failure in the exception thrown whenever a waiter transitions to a failure state.

## __AWS Secrets Manager__
  - ### Features
    - add v2 smoke tests and smithy smokeTests trait for SDK testing

## __Agents for Amazon Bedrock__
  - ### Features
    - This release adds support for using Guardrails with Bedrock Agents.

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - This release adds support for using Guardrails with Bedrock Agents.

## __Amazon OpenSearch Ingestion__
  - ### Features
    - Add support for creating an OpenSearch Ingestion pipeline that is attached to a provided VPC. Add information about the destinations of an OpenSearch Ingestion pipeline to the GetPipeline and ListPipelines APIs.

## __Amazon Relational Database Service__
  - ### Features
    - This release adds support for EngineLifecycleSupport on DBInstances, DBClusters, and GlobalClusters.

# __2.25.55__ __2024-05-17__
## __AWS CodeBuild__
  - ### Features
    - Aws CodeBuild now supports 36 hours build timeout

## __AWS Lake Formation__
  - ### Features
    - Introduces a new API, GetDataLakePrincipal, that returns the identity of the invoking principal

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

  - ### Bugfixes
    - Prevent calculating crc32 checksum on an incomplete response, due to error

## __AWS Transfer Family__
  - ### Features
    - Enable use of CloudFormation traits in Smithy model to improve generated CloudFormation schema from the Smithy API model.

## __Amazon S3__
  - ### Bugfixes
    - Fixes bug where pre-signed URL expiration duration using AwsS3V4Signer may get rounded down 1 second

## __Amazon Simple Storage Service (S3)__
  - ### Bugfixes
    - Decoded encoded keys in the ListObjectVersions.DeleteMarker field

## __Application Auto Scaling__
  - ### Features
    - add v2 smoke tests and smithy smokeTests trait for SDK testing.

## __Elastic Load Balancing__
  - ### Features
    - This release adds dualstack-without-public-ipv4 IP address type for ALB.

# __2.25.54__ __2024-05-16__
## __AWS Certificate Manager Private Certificate Authority__
  - ### Features
    - This release adds support for waiters to fail on AccessDeniedException when having insufficient permissions

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Secrets Manager__
  - ### Features
    - Documentation updates for AWS Secrets Manager

## __Amazon Connect Service__
  - ### Features
    - Adding Contact Flow metrics to the GetMetricDataV2 API

## __Amazon QuickSight__
  - ### Features
    - This release adds DescribeKeyRegistration and UpdateKeyRegistration APIs to manage QuickSight Customer Managed Keys (CMK).

## __Amazon SageMaker Service__
  - ### Features
    - Introduced WorkerAccessConfiguration to SageMaker Workteam. This allows customers to configure resource access for workers in a workteam.

## __AmazonMWAA__
  - ### Features
    - Amazon MWAA now supports Airflow web server auto scaling to automatically handle increased demand from REST APIs, Command Line Interface (CLI), or more Airflow User Interface (UI) users. Customers can specify maximum and minimum web server instances during environment creation and update workflow.

## __Managed Streaming for Kafka__
  - ### Features
    - AWS MSK support for Broker Removal.

## __S3 Transfer Manager__
  - ### Bugfixes
    - Skip downloading S3 folders (0-content-length folders created in the S3 console) in downloadDirectory in the S3 Transfer Manager.

# __2.25.53__ __2024-05-15__
## __AWS CodeBuild__
  - ### Features
    - CodeBuild Reserved Capacity VPC Support

## __AWS DataSync__
  - ### Features
    - Task executions now display a CANCELLING status when an execution is in the process of being cancelled.

## __AWS Health Imaging__
  - ### Features
    - Added support for importing medical imaging data from Amazon S3 buckets across accounts and regions.

## __AWS SDK for Java v2__
  - ### Bugfixes
    - Fixed a bug in file-based AsyncResponseTransformer that could cause a streaming request to hang if an exception was thrown from `onStream`

## __AWS SecurityHub__
  - ### Features
    - Documentation-only update for AWS Security Hub

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - Updating Bedrock Knowledge Base Metadata & Filters feature with two new filters listContains and stringContains

## __Amazon CloudFront__
  - ### Bugfixes
    - Fixes bug where SignedUrl strips the port number from the url.

## __Amazon Managed Grafana__
  - ### Features
    - This release adds new ServiceAccount and ServiceAccountToken APIs.

# __2.25.52__ __2024-05-14__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Connect Service__
  - ### Features
    - Amazon Connect provides enhanced search capabilities for flows & flow modules on the Connect admin website and programmatically using APIs. You can search for flows and flow modules by name, description, type, status, and tags, to filter and identify a specific flow in your Connect instances.

## __Amazon Simple Storage Service__
  - ### Features
    - Updated a few x-id in the http uri traits

# __2.25.51__ __2024-05-13__
## __AWS SDK for Java v2__
  - ### Features
    - Adds capability to resolve endpoint and auth scheme parameters of type list of string as well as new functions to JmesPath runtime (keys and wildcard)
    - Updated endpoint and partition metadata.

## __Amazon EventBridge__
  - ### Features
    - Amazon EventBridge introduces KMS customer-managed key (CMK) encryption support for custom and partner events published on EventBridge Event Bus (including default bus) and UpdateEventBus API.

## __Amazon VPC Lattice__
  - ### Features
    - This release adds TLS Passthrough support. It also increases max number of target group per rule to 10.

# __2.25.50__ __2024-05-10__
## __AWS Application Discovery Service__
  - ### Features
    - add v2 smoke tests and smithy smokeTests trait for SDK testing

## __AWS IoT Greengrass V2__
  - ### Features
    - Mark ComponentVersion in ComponentDeploymentSpecification as required.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS SSO OIDC__
  - ### Features
    - Updated request parameters for PKCE support.

## __Amazon SageMaker Service__
  - ### Features
    - Introduced support for G6 instance types on Sagemaker Notebook Instances and on SageMaker Studio for JupyterLab and CodeEditor applications.

# __2.25.49__ __2024-05-09__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Systems Manager for SAP__
  - ### Features
    - Added support for application-aware start/stop of SAP applications running on EC2 instances, with SSM for SAP

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - This release adds support to provide guardrail configuration and modify inference parameters that are then used in RetrieveAndGenerate API in Agents for Amazon Bedrock.

## __Amazon Pinpoint__
  - ### Features
    - This release adds support for specifying email message headers for Email Templates, Campaigns, Journeys and Send Messages.

## __Amazon Route 53 Resolver__
  - ### Features
    - Update the DNS Firewall settings to correct a spelling issue.

## __Amazon Verified Permissions__
  - ### Features
    - Adds policy effect and actions fields to Policy API's.

# __2.25.48__ __2024-05-08__
## __AWS SDK for Java v2 Bundle__
  - ### Bugfixes
    - Fix unshaded classes in the AWS SDK for Java v2 bundle. See [#5108](https://github.com/aws/aws-sdk-java-v2/issues/5108)

## __Amazon Cognito Identity Provider__
  - ### Features
    - Add EXTERNAL_PROVIDER enum value to UserStatusType.

## __Amazon EC2 Container Registry__
  - ### Features
    - This release adds pull through cache rules support for GitLab container registry in Amazon ECR.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Adding Precision Hardware Clock (PHC) to public API DescribeInstanceTypes

## __Amazon Polly__
  - ### Features
    - Add new engine - generative - that builds the most expressive conversational voices.

## __Amazon Simple Queue Service__
  - ### Features
    - This release adds MessageSystemAttributeNames to ReceiveMessageRequest to replace AttributeNames.

## __Firewall Management Service__
  - ### Features
    - The policy scope resource tag is always a string value, either a non-empty string or an empty string.

# __2.25.47__ __2024-05-07__
## __AWS B2B Data Interchange__
  - ### Features
    - Documentation update to clarify the MappingTemplate definition.

## __AWS Budgets__
  - ### Features
    - This release adds tag support for budgets and budget actions.

## __AWS Resilience Hub__
  - ### Features
    - AWS Resilience Hub has expanded its drift detection capabilities by introducing a new type of drift detection - application resource drift. This new enhancement detects changes, such as the addition or deletion of resources within the application's input sources.

## __Route 53 Profiles__
  - ### Features
    - Doc only update for Route 53 profiles that fixes some link issues

## __s3-transfer-manager__
  - ### Bugfixes
    - Added reflect-config.json for ApplyUserAgentInterceptor in s3-transfer-manager for native builds
        - Contributed by: [@klopfdreh](https://github.com/klopfdreh)

## __Contributors__
Special thanks to the following contributors to this release: 

[@klopfdreh](https://github.com/klopfdreh)
# __2.25.46__ __2024-05-06__
## __AWS Elemental MediaLive__
  - ### Features
    - AWS Elemental MediaLive now supports configuring how SCTE 35 passthrough triggers segment breaks in HLS and MediaPackage output groups. Previously, messages triggered breaks in all these output groups. The new option is to trigger segment breaks only in groups that have SCTE 35 passthrough enabled.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

# __2.25.45__ __2024-05-03__
## __AWS DataSync__
  - ### Features
    - Updated guidance on using private or self-signed certificate authorities (CAs) with AWS DataSync object storage locations.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Agents for Amazon Bedrock__
  - ### Features
    - This release adds support for using Provisioned Throughput with Bedrock Agents.

## __Amazon Connect Cases__
  - ### Features
    - This feature supports the release of Files related items

## __Amazon Connect Service__
  - ### Features
    - This release adds 5 new APIs for managing attachments: StartAttachedFileUpload, CompleteAttachedFileUpload, GetAttachedFile, BatchGetAttachedFileMetadata, DeleteAttachedFile. These APIs can be used to programmatically upload and download attachments to Connect resources, like cases.

## __Amazon SageMaker Service__
  - ### Features
    - Amazon SageMaker Inference now supports m6i, c6i, r6i, m7i, c7i, r7i and g5 instance types for Batch Transform Jobs

## __Amazon Simple Email Service__
  - ### Features
    - Adds support for specifying replacement headers per BulkEmailEntry in SendBulkEmail in SESv2.

## __Inspector2__
  - ### Features
    - This release adds CSV format to GetCisScanReport for Inspector v2

# __2.25.44__ __2024-05-02__
## __Amazon DynamoDB__
  - ### Features
    - This release adds support to specify an optional, maximum OnDemandThroughput for DynamoDB tables and global secondary indexes in the CreateTable or UpdateTable APIs. You can also override the OnDemandThroughput settings by calling the ImportTable, RestoreFromPointInTime, or RestoreFromBackup APIs.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - This release includes a new API for retrieving the public endorsement key of the EC2 instance's Nitro Trusted Platform Module (NitroTPM).

## __Amazon Personalize__
  - ### Features
    - This releases ability to delete users and their data, including their metadata and interactions data, from a dataset group.

## __Redshift Serverless__
  - ### Features
    - Update Redshift Serverless List Scheduled Actions Output Response to include Namespace Name.

# __2.25.43__ __2024-05-01__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS SecurityHub__
  - ### Features
    - Updated CreateMembers API request with limits.

## __Agents for Amazon Bedrock__
  - ### Features
    - This release adds support for using MongoDB Atlas as a vector store when creating a knowledge base.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Documentation updates for Amazon EC2.

## __Amazon Personalize Runtime__
  - ### Features
    - This release adds support for a Reason attribute for predicted items generated by User-Personalization-v2.

## __Amazon Simple Email Service__
  - ### Features
    - Fixes ListContacts and ListImportJobs APIs to use POST instead of GET.

# __2.25.42__ __2024-04-30__
## __AWS Signer__
  - ### Features
    - Documentation updates for AWS Signer. Adds cross-account signing constraint and definitions for cross-account actions.

## __Amazon Chime SDK Voice__
  - ### Features
    - Due to changes made by the Amazon Alexa service, GetSipMediaApplicationAlexaSkillConfiguration and PutSipMediaApplicationAlexaSkillConfiguration APIs are no longer available for use. For more information, refer to the Alexa Smart Properties page.

## __Amazon Omics__
  - ### Features
    - Add support for workflow sharing and dynamic run storage

## __Amazon OpenSearch Service__
  - ### Features
    - This release enables customers to create Route53 A and AAAA alias record types to point custom endpoint domain to OpenSearch domain's dualstack search endpoint.

## __Amazon Pinpoint SMS Voice V2__
  - ### Features
    - Amazon Pinpoint has added two new features Multimedia services (MMS) and protect configurations. Use the three new MMS APIs to send media messages to a mobile phone which includes image, audio, text, or video files. Use the ten new protect configurations APIs to block messages to specific countries.

## __Amazon QuickSight__
  - ### Features
    - New Q embedding supporting Generative Q&A

## __Amazon Route 53 Resolver__
  - ### Features
    - Release of FirewallDomainRedirectionAction parameter on the Route 53 DNS Firewall Rule. This allows customers to configure a DNS Firewall rule to inspect all the domains in the DNS redirection chain (default) , such as CNAME, ALIAS, DNAME, etc., or just the first domain and trust the rest.

## __Amazon SageMaker Service__
  - ### Features
    - Amazon SageMaker Training now supports the use of attribute-based access control (ABAC) roles for training job execution roles. Amazon SageMaker Inference now supports G6 instance types.

## __CodeArtifact__
  - ### Features
    - Add support for the Ruby package format.

## __Firewall Management Service__
  - ### Features
    - AWS Firewall Manager now supports the network firewall service stream exception policy feature for accounts within your organization.

## __QBusiness__
  - ### Features
    - This is a general availability (GA) release of Amazon Q Business. Q Business enables employees in an enterprise to get comprehensive answers to complex questions and take actions through a unified, intuitive web-based chat experience - using an enterprise's existing content, data, and systems.

# __2.25.41__ __2024-04-29__
## __AWS Amplify__
  - ### Features
    - Updating max results limit for listing any resources (Job, Artifacts, Branch, BackendResources, DomainAssociation) to 50 with the exception of list apps that where max results can be up to 100.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

  - ### Bugfixes
    - Log `AWS4 Canonical Request` in signer if DEBUG level is enabled.

## __Amazon Connect Cases__
  - ### Features
    - This feature releases DeleteField, DeletedLayout, and DeleteTemplate API's

## __Amazon Timestream Query__
  - ### Features
    - This change allows users to update and describe account settings associated with their accounts.

## __Amazon Transcribe Service__
  - ### Features
    - This update provides error messaging for generative call summarization in Transcribe Call Analytics

## __Inspector2__
  - ### Features
    - Update Inspector2 to include new Agentless API parameters.

## __TrustedAdvisor Public API__
  - ### Features
    - This release adds the BatchUpdateRecommendationResourceExclusion API to support batch updates of Recommendation Resource exclusion statuses and introduces a new exclusion status filter to the ListRecommendationResources and ListOrganizationRecommendationResources APIs.

# __2.25.40__ __2024-04-26__
## __AWS CodePipeline__
  - ### Features
    - Add ability to manually and automatically roll back a pipeline stage to a previously successful execution.

## __AWS Marketplace Entitlement Service__
  - ### Features
    - Releasing minor endpoint updates.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Support__
  - ### Features
    - Releasing minor endpoint updates.

## __Amazon Cognito Identity Provider__
  - ### Features
    - Add LimitExceededException to SignUp errors

## __Amazon Relational Database Service__
  - ### Features
    - SupportsLimitlessDatabase field added to describe-db-engine-versions to indicate whether the DB engine version supports Aurora Limitless Database.

## __AmazonConnectCampaignService__
  - ### Features
    - This release adds support for specifying if Answering Machine should wait for prompt sound.

## __CloudWatch Observability Access Manager__
  - ### Features
    - This release introduces support for Source Accounts to define which Metrics and Logs to share with the Monitoring Account

# __2.25.39__ __2024-04-25__
## __AWS AppSync__
  - ### Features
    - UpdateGraphQLAPI documentation update and datasource introspection secret arn update

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Step Functions__
  - ### Features
    - Add new ValidateStateMachineDefinition operation, which performs syntax checking on the definition of a Amazon States Language (ASL) state machine.

## __Amazon Interactive Video Service__
  - ### Features
    - Bug Fix: IVS does not support arns with the `svs` prefix

## __Amazon Interactive Video Service RealTime__
  - ### Features
    - Bug Fix: IVS Real Time does not support ARNs using the `svs` prefix.

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for setting local time zones for RDS for Db2 DB instances.

## __Firewall Management Service__
  - ### Features
    - AWS Firewall Manager adds support for network ACL policies to manage Amazon Virtual Private Cloud (VPC) network access control lists (ACLs) for accounts in your organization.

# __2.25.38__ __2024-04-24__
## __AWS DataSync__
  - ### Features
    - This change allows users to disable and enable the schedules associated with their tasks.

## __AWS EntityResolution__
  - ### Features
    - Support Batch Unique IDs Deletion.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon EMR Containers__
  - ### Features
    - EMRonEKS Service support for SecurityConfiguration enforcement for Spark Jobs.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Launching capability for customers to enable or disable automatic assignment of public IPv4 addresses to their network interface

## __Amazon GameLift__
  - ### Features
    - Amazon GameLift releases container fleets support for public preview. Deploy Linux-based containerized game server software for hosting on Amazon GameLift.

## __Amazon Simple Systems Manager (SSM)__
  - ### Features
    - Add SSM DescribeInstanceProperties API to public AWS SDK.

# __2.25.37__ __2024-04-23__
## __AWS Cost Explorer Service__
  - ### Features
    - Added additional metadata that might be applicable to your reservation recommendations.

## __AWS Performance Insights__
  - ### Features
    - Clarifies how aggregation works for GetResourceMetrics in the Performance Insights API.

## __AWS SDK for Java V2__
  - ### Features
    - Source account Id from credentials to use in endpoint construction

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Agents for Amazon Bedrock__
  - ### Features
    - Introducing the ability to create multiple data sources per knowledge base, specify S3 buckets as data sources from external accounts, and exposing levers to define the deletion behavior of the underlying vector store data.

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - This release introduces zero-setup file upload support for the RetrieveAndGenerate API. This allows you to chat with your data without setting up a Knowledge Base.

## __Amazon Bedrock__
  - ### Features
    - This release introduces Model Evaluation and Guardrails for Amazon Bedrock.

## __Amazon Bedrock Runtime__
  - ### Features
    - This release introduces Guardrails for Amazon Bedrock.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - This release introduces EC2 AMI Deregistration Protection, a new AMI property that can be enabled by customers to protect an AMI against an unintended deregistration. This release also enables the AMI owners to view the AMI 'LastLaunchedTime' in DescribeImages API.

## __Amazon Relational Database Service__
  - ### Features
    - Fix the example ARN for ModifyActivityStreamRequest

## __Amazon WorkSpaces Web__
  - ### Features
    - Added InstanceType and MaxConcurrentSessions parameters on CreatePortal and UpdatePortal Operations as well as the ability to read Customer Managed Key & Additional Encryption Context parameters on supported resources (Portal, BrowserSettings, UserSettings, IPAccessSettings)

# __2.25.36__ __2024-04-22__
## __AWS Cloud Map__
  - ### Features
    - This release adds examples to several Cloud Map actions.

## __AWS Transfer Family__
  - ### Features
    - Adding new API to support remote directory listing using SFTP connector

## __Agents for Amazon Bedrock__
  - ### Features
    - Releasing the support for simplified configuration and return of control

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - Releasing the support for simplified configuration and return of control

## __Amazon SageMaker Service__
  - ### Features
    - This release adds support for Real-Time Collaboration and Shared Space for JupyterLab App on SageMaker Studio.

## __Payment Cryptography Control Plane__
  - ### Features
    - Adding support to TR-31/TR-34 exports for optional headers, allowing customers to add additional metadata (such as key version and KSN) when exporting keys from the service.

## __Redshift Serverless__
  - ### Features
    - Updates description of schedule field for scheduled actions.

## __Route 53 Profiles__
  - ### Features
    - Route 53 Profiles allows you to apply a central DNS configuration across many VPCs regardless of account.

# __2.25.35__ __2024-04-19__
## __AWS Glue__
  - ### Features
    - Adding RowFilter in the response for GetUnfilteredTableMetadata API

## __Amazon CloudWatch Internet Monitor__
  - ### Features
    - This update introduces the GetInternetEvent and ListInternetEvents APIs, which provide access to internet events displayed on the Amazon CloudWatch Internet Weather Map.

## __Amazon Personalize__
  - ### Features
    - This releases auto training capability while creating a solution and automatically syncing latest solution versions when creating/updating a campaign

# __2.25.34__ __2024-04-18__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon GuardDuty__
  - ### Features
    - Added IPv6Address fields for local and remote IP addresses

## __Amazon QuickSight__
  - ### Features
    - This release adds support for the Cross Sheet Filter and Control features, and support for warnings in asset imports for any permitted errors encountered during execution

## __Amazon SageMaker Service__
  - ### Features
    - Removed deprecated enum values and updated API documentation.

## __Amazon WorkSpaces__
  - ### Features
    - Adds new APIs for managing and sharing WorkSpaces BYOL configuration across accounts.

## __EMR Serverless__
  - ### Features
    - This release adds the capability to publish detailed Spark engine metrics to Amazon Managed Service for Prometheus (AMP) for enhanced monitoring for Spark jobs.

## __Elastic Disaster Recovery Service__
  - ### Features
    - Outpost ARN added to Source Server and Recovery Instance

## __IAM Roles Anywhere__
  - ### Features
    - This release introduces the PutAttributeMapping and DeleteAttributeMapping APIs. IAM Roles Anywhere now provides the capability to define a set of mapping rules, allowing customers to specify which data is extracted from their X.509 end-entity certificates.

# __2.25.33__ __2024-04-17__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Documentation updates for Elastic Compute Cloud (EC2).

## __QBusiness__
  - ### Features
    - This release adds support for IAM Identity Center (IDC) as the identity gateway for Q Business. It also allows users to provide an explicit intent for Q Business to identify how the Chat request should be handled.

# __2.25.32__ __2024-04-16__
## __AWS Elemental MediaPackage v2__
  - ### Features
    - Dash v2 is a MediaPackage V2 feature to support egressing on DASH manifest format.

## __AWS EntityResolution__
  - ### Features
    - Cross Account Resource Support .

## __AWS IoT Wireless__
  - ### Features
    - Add PublicGateways in the GetWirelessStatistics call response, indicating the LoRaWAN public network accessed by the device.

## __AWS Lake Formation__
  - ### Features
    - This release adds Lake Formation managed RAM support for the 4 APIs - "DescribeLakeFormationIdentityCenterConfiguration", "CreateLakeFormationIdentityCenterConfiguration", "DescribeLakeFormationIdentityCenterConfiguration", and "DeleteLakeFormationIdentityCenterConfiguration"

## __AWS Outposts__
  - ### Features
    - This release adds new APIs to allow customers to configure their Outpost capacity at order-time.

## __AWS SDK for Java v2__
  - ### Features
    - Added support for Waiters specifically for Matchers with Error to accept true/false value not as string but as boolean values such that True value is to match on any error code, or boolean false to test if no errors were encountered as per the SDK Waiter specs.
    - Updated endpoint and partition metadata.

## __AWS Well-Architected Tool__
  - ### Features
    - AWS Well-Architected now has a Connector for Jira to allow customers to efficiently track workload risks and improvement efforts and create closed-loop mechanisms.

## __AWSMainframeModernization__
  - ### Features
    - Adding new ListBatchJobRestartPoints API and support for restart batch job.

## __Agents for Amazon Bedrock__
  - ### Features
    - For Create Agent API, the agentResourceRoleArn parameter is no longer required.

## __EMR Serverless__
  - ### Features
    - This release adds support for shuffle optimized disks that allow larger disk sizes and higher IOPS to efficiently run shuffle heavy workloads.

# __2.25.31__ __2024-04-12__
## __AWS CloudFormation__
  - ### Features
    - Adding support for the new parameter "IncludePropertyValues" in the CloudFormation DescribeChangeSet API. When this parameter is included, the DescribeChangeSet response will include more detailed information such as before and after values for the resource properties that will change.

## __AWS Config__
  - ### Features
    - Updates documentation for AWS Config

## __AWS Glue__
  - ### Features
    - Modifying request for GetUnfilteredTableMetadata for view-related fields.

## __AWS IoT Fleet Hub__
  - ### Features
    - Documentation updates for AWS IoT Fleet Hub to clarify that Fleet Hub supports organization instance of IAM Identity Center.

## __AWS Key Management Service__
  - ### Features
    - This feature supports the ability to specify a custom rotation period for automatic key rotations, the ability to perform on-demand key rotations, and visibility into your key material rotations.

## __AWS MediaTailor__
  - ### Features
    - Added InsertionMode to PlaybackConfigurations. This setting controls whether players can use stitched or guided ad insertion. The default for players that do not specify an insertion mode is stitched.

## __AWS Outposts__
  - ### Features
    - This release adds EXPEDITORS as a valid shipment carrier.

## __AWS Transfer Family__
  - ### Features
    - This change releases support for importing self signed certificates to the Transfer Family for sending outbound file transfers over TLS/HTTPS.

## __Amazon HealthLake__
  - ### Features
    - Added new CREATE_FAILED status for data stores. Added new errorCause to DescribeFHIRDatastore API and ListFHIRDatastores API response for additional insights into data store creation and deletion workflows.

## __Amazon Neptune Graph__
  - ### Features
    - Update to API documentation to resolve customer reported issues.

## __Amazon Redshift__
  - ### Features
    - Adds support for Amazon Redshift DescribeClusterSnapshots API to include Snapshot ARN response field.

# __2.25.30__ __2024-04-11__
## __AWS Batch__
  - ### Features
    - This release adds the task properties field to attempt details and the name field on EKS container detail.

## __AWS CodeBuild__
  - ### Features
    - Support access tokens for Bitbucket sources

## __AWS Elemental MediaLive__
  - ### Features
    - AWS Elemental MediaLive introduces workflow monitor, a new feature that enables the visualization and monitoring of your media workflows. Create signal maps of your existing workflows and monitor them by creating notification and monitoring template groups.

## __AWS Identity and Access Management__
  - ### Features
    - For CreateOpenIDConnectProvider API, the ThumbprintList parameter is no longer required.

## __AWS S3 Control__
  - ### Features
    - Documentation updates for Amazon S3-control.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS WAFV2__
  - ### Features
    - Adds an updated version of smoke tests, including smithy trait, for SDK testing.

## __Amazon CloudFront__
  - ### Features
    - CloudFront origin access control extends support to AWS Lambda function URLs and AWS Elemental MediaPackage v2 origins.

## __Amazon CloudWatch__
  - ### Features
    - This release adds support for Metric Characteristics for CloudWatch Anomaly Detection. Anomaly Detector now takes Metric Characteristics object with Periodic Spikes boolean field that tells Anomaly Detection that spikes that repeat at the same time every week are part of the expected pattern.

## __Amazon EventBridge Pipes__
  - ### Features
    - LogConfiguration ARN validation fixes

## __Amazon Omics__
  - ### Features
    - This release adds support for retrieval of S3 direct access metadata on sequence stores and read sets, and adds support for SHA256up and SHA512up HealthOmics ETags.

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for Standard Edition 2 support in RDS Custom for Oracle.

# __2.25.29__ __2024-04-10__
## __AWS Clean Rooms Service__
  - ### Features
    - AWS Clean Rooms Differential Privacy is now fully available. Differential privacy protects against user-identification attempts.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

  - ### Bugfixes
    - Set keepAile in SocketOptions to true if TcpKeepAliveConfiguration is set.
        - Contributed by: [@eckardnet](https://github.com/eckardnet)

## __AWS Supply Chain__
  - ### Features
    - This release includes API SendDataIntegrationEvent for AWS Supply Chain

## __Amazon CloudWatch Network Monitor__
  - ### Features
    - Examples were added to CloudWatch Network Monitor commands.

## __Amazon Connect Service__
  - ### Features
    - This release adds new Submit Auto Evaluation Action for Amazon Connect Rules.

## __Amazon Q Connect__
  - ### Features
    - This release adds a new QiC public API updateSession and updates an existing QiC public API createSession

## __Amazon Rekognition__
  - ### Features
    - Added support for ContentType to content moderation detections.

## __Amazon WorkSpaces Thin Client__
  - ### Features
    - Adding tags field to SoftwareSet. Removing tags fields from Summary objects. Changing the list of exceptions in tagging APIs. Fixing an issue where the SDK returns empty tags in Get APIs.

## __Contributors__
Special thanks to the following contributors to this release: 

[@eckardnet](https://github.com/eckardnet)
# __2.25.28__ __2024-04-09__
## __AWS CodeBuild__
  - ### Features
    - Add new webhook filter types for GitHub webhooks

## __AWS Elemental MediaConvert__
  - ### Features
    - This release includes support for bringing your own fonts to use for burn-in or DVB-Sub captioning workflows.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Pinpoint__
  - ### Features
    - The OrchestrationSendingRoleArn has been added to the email channel and is used to send emails from campaigns or journeys.

## __Amazon Relational Database Service__
  - ### Features
    - This release adds support for specifying the CA certificate to use for the new db instance when restoring from db snapshot, restoring from s3, restoring to point in time, and creating a db instance read replica.

# __2.25.27__ __2024-04-08__
## __AWS Control Catalog__
  - ### Features
    - This is the initial SDK release for AWS Control Catalog, a central catalog for AWS managed controls. This release includes 3 new APIs - ListDomains, ListObjectives, and ListCommonControls - that vend high-level data to categorize controls across the AWS platform.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon CloudWatch Network Monitor__
  - ### Features
    - Updated the allowed monitorName length for CloudWatch Network Monitor.

## __Application Migration Service__
  - ### Features
    - Added USE_SOURCE as default option to LaunchConfigurationTemplate bootMode parameter.

# __2.25.26__ __2024-04-05__
## __AWS Resource Groups__
  - ### Features
    - Added a new QueryErrorCode RESOURCE_TYPE_NOT_SUPPORTED that is returned by the ListGroupResources operation if the group query contains unsupported resource types.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon QuickSight__
  - ### Features
    - Adding IAMIdentityCenterInstanceArn parameter to CreateAccountSubscription

## __Amazon Verified Permissions__
  - ### Features
    - Adding BatchIsAuthorizedWithToken API which supports multiple authorization requests against a PolicyStore given a bearer token.

# __2.25.25__ __2024-04-04__
## __AWS B2B Data Interchange__
  - ### Features
    - Adding support for X12 5010 HIPAA EDI version and associated transaction sets.

## __AWS Clean Rooms Service__
  - ### Features
    - Feature: New schemaStatusDetails field to the existing Schema object that displays a status on Schema API responses to show whether a schema is queryable or not. New BatchGetSchemaAnalysisRule API to retrieve multiple schemaAnalysisRules using a single API call.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon EMR Containers__
  - ### Features
    - This release adds support for integration with EKS AccessEntry APIs to enable automatic Cluster Access for EMR on EKS.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Amazon EC2 G6 instances powered by NVIDIA L4 Tensor Core GPUs can be used for a wide range of graphics-intensive and machine learning use cases. Gr6 instances also feature NVIDIA L4 GPUs and can be used for graphics workloads with higher memory requirements.

## __Amazon Interactive Video Service__
  - ### Features
    - API update to include an SRT ingest endpoint and passphrase for all channels.

## __Amazon S3__
  - ### Features
    - Implement TransferListener for copy operations when using TransferManager with Java-based S3Client with multipart enabled

## __Amazon Verified Permissions__
  - ### Features
    - Adds GroupConfiguration field to Identity Source API's

## __S3__
  - ### Documentations
    - improve javadoc for S3 Event Notification module.

# __2.25.24__ __2024-04-03__
## __AWS Clean Rooms ML__
  - ### Features
    - The release includes a public SDK for AWS Clean Rooms ML APIs, making them globally available to developers worldwide.

## __AWS CloudFormation__
  - ### Features
    - This release would return a new field - PolicyAction in cloudformation's existed DescribeChangeSetResponse, showing actions we are going to apply on the physical resource (e.g., Delete, Retain) according to the user's template

## __AWS Elemental MediaLive__
  - ### Features
    - Cmaf Ingest outputs are now supported in Media Live

## __AWS Ground Station__
  - ### Features
    - This release adds visibilityStartTime and visibilityEndTime to DescribeContact and ListContacts responses.

## __AWS Health Imaging__
  - ### Features
    - SearchImageSets API now supports following enhancements - Additional support for searching on UpdatedAt and SeriesInstanceUID - Support for searching existing filters between dates/times - Support for sorting the search result by Ascending/Descending - Additional parameters returned in the response

## __AWS Lambda__
  - ### Features
    - Add Ruby 3.3 (ruby3.3) support to AWS Lambda

## __AWS SDK for Java v2__
  - ### Features
    - Records identity provider names in a resolved identity and adds this information to the user agent string
    - Updated endpoint and partition metadata.

## __AWS Transfer Family__
  - ### Features
    - Add ability to specify Security Policies for SFTP Connectors

## __Amazon DataZone__
  - ### Features
    - This release supports the feature of dataQuality to enrich asset with dataQualityResult in Amazon DataZone.

## __Amazon DocumentDB with MongoDB compatibility__
  - ### Features
    - This release adds Global Cluster Switchover capability which enables you to change your global cluster's primary AWS Region, the region that serves writes, while preserving the replication between all regions in the global cluster.

# __2.25.23__ __2024-04-02__
## __AWS Glue__
  - ### Features
    - Adding View related fields to responses of read-only Table APIs.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS SecurityHub__
  - ### Features
    - Documentation updates for AWS Security Hub

## __Amazon DynamoDB Enhanced Client__
  - ### Bugfixes
    - Fixed toJson function in EnhancedDocument API to properly handle unicode control characters by escaping them with backslashes, preventing JsonParseException.

## __Amazon EC2 Container Service__
  - ### Features
    - Documentation only update for Amazon ECS.

## __Amazon Interactive Video Service Chat__
  - ### Features
    - Doc-only update. Changed "Resources" to "Key Concepts" in docs and updated text.

## __IAM Roles Anywhere__
  - ### Features
    - This release increases the limit on the roleArns request parameter for the *Profile APIs that support it. This parameter can now take up to 250 role ARNs.

# __2.25.22__ __2024-04-01__
## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWSDeadlineCloud__
  - ### Features
    - AWS Deadline Cloud is a new fully managed service that helps customers set up, deploy, and scale rendering projects in minutes, so they can improve the efficiency of their rendering pipelines and take on more projects.

## __Amazon CloudWatch__
  - ### Features
    - This release adds support for CloudWatch Anomaly Detection on cross-account metrics. SingleMetricAnomalyDetector and MetricDataQuery inputs to Anomaly Detection APIs now take an optional AccountId field.

## __Amazon DataZone__
  - ### Features
    - This release supports the feature of AI recommendations for descriptions to enrich the business data catalog in Amazon DataZone.

## __Amazon EMR__
  - ### Features
    - This release fixes a broken link in the documentation.

## __Amazon Lightsail__
  - ### Features
    - This release adds support to upgrade the TLS version of the distribution.

# __2.25.21__ __2024-03-29__
## __AWS B2B Data Interchange__
  - ### Features
    - Supporting new EDI X12 transaction sets for X12 versions 4010, 4030, and 5010.

## __AWS CodeBuild__
  - ### Features
    - Add new fleet status code for Reserved Capacity.

## __AWS CodeConnections__
  - ### Features
    - Duplicating the CodeStar Connections service into the new, rebranded AWS CodeConnections service.

## __AWS IoT Wireless__
  - ### Features
    - Add support for retrieving key historical and live metrics for LoRaWAN devices and gateways

## __AWS Marketplace Catalog Service__
  - ### Features
    - This release enhances the ListEntities API to support ResaleAuthorizationId filter and sort for OfferEntity in the request and the addition of a ResaleAuthorizationId field in the response of OfferSummary.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon CloudWatch Internet Monitor__
  - ### Features
    - This release adds support to allow customers to track cross account monitors through ListMonitor, GetMonitor, ListHealthEvents, GetHealthEvent, StartQuery APIs.

## __Amazon Neptune Graph__
  - ### Features
    - Add the new API Start-Import-Task for Amazon Neptune Analytics.

## __Amazon SageMaker Service__
  - ### Features
    - This release adds support for custom images for the CodeEditor App on SageMaker Studio

# __2.25.20__ __2024-03-28__
## __AWS Compute Optimizer__
  - ### Features
    - This release enables AWS Compute Optimizer to analyze and generate recommendations with a new customization preference, Memory Utilization.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon CodeCatalyst__
  - ### Features
    - This release adds support for understanding pending changes to subscriptions by including two new response parameters for the GetSubscription API for Amazon CodeCatalyst.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Amazon EC2 C7gd, M7gd and R7gd metal instances with up to 3.8 TB of local NVMe-based SSD block-level storage have up to 45% improved real-time NVMe storage performance than comparable Graviton2-based instances.

## __Amazon Elastic Kubernetes Service__
  - ### Features
    - Add multiple customer error code to handle customer caused failure when managing EKS node groups

## __Amazon GuardDuty__
  - ### Features
    - Add EC2 support for GuardDuty Runtime Monitoring auto management.

## __Amazon Neptune Graph__
  - ### Features
    - Update ImportTaskCancelled waiter to evaluate task state correctly and minor documentation changes.

## __Amazon QuickSight__
  - ### Features
    - Amazon QuickSight: Adds support for setting up VPC Endpoint restrictions for accessing QuickSight Website.

## __CloudWatch Observability Access Manager__
  - ### Features
    - This release adds support for sharing AWS::InternetMonitor::Monitor resources.

# __2.25.19__ __2024-03-27__
## __AWS Batch__
  - ### Features
    - This feature allows AWS Batch to support configuration of imagePullSecrets and allowPrivilegeEscalation for jobs running on EKS

## __AWS SDK For Java v2__
  - ### Bugfixes
    - Remediate CVE-2024-29025 by updating netty version
        - Contributed by: [@FatalEnigma](https://github.com/FatalEnigma)

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Secrets Manager__
  - ### Features
    - Documentation updates for Secrets Manager

## __Agents for Amazon Bedrock__
  - ### Features
    - This changes introduces metadata documents statistics and also updates the documentation for bedrock agent.

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - This release introduces filtering support on Retrieve and RetrieveAndGenerate APIs.

## __Amazon ElastiCache__
  - ### Features
    - Added minimum capacity to Amazon ElastiCache Serverless. This feature allows customer to ensure minimum capacity even without current load

## __S3 Transfer Manager__
  - ### Bugfixes
    - Set a limit on the number of concurrent upload file requests for upload directory. This fixes the OOM issue that could surface when users try to upload a directory that has millions of small files. See [#5023](https://github.com/aws/aws-sdk-java-v2/issues/5023).

## __Contributors__
Special thanks to the following contributors to this release: 

[@FatalEnigma](https://github.com/FatalEnigma)
# __2.25.18__ __2024-03-26__
## __AWS Cost Explorer Service__
  - ### Features
    - Adds support for backfill of cost allocation tags, with new StartCostAllocationTagBackfill and ListCostAllocationTagBackfillHistory API.

## __AWS S3__
  - ### Features
    - allow user to configure subscriber timeout for input stream
        - Contributed by: [@benarnao](https://github.com/benarnao)

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - This release adds support to customize prompts sent through the RetrieveAndGenerate API in Agents for Amazon Bedrock.

## __Amazon EC2 Container Service__
  - ### Features
    - This is a documentation update for Amazon ECS.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Documentation updates for Elastic Compute Cloud (EC2).

## __FinSpace User Environment Management service__
  - ### Features
    - Add new operation delete-kx-cluster-node and add status parameter to list-kx-cluster-node operation.

## __Contributors__
Special thanks to the following contributors to this release: 

[@benarnao](https://github.com/benarnao)
# __2.25.17__ __2024-03-25__
## __AWS CodeBuild__
  - ### Features
    - Supporting GitLab and GitLab Self Managed as source types in AWS CodeBuild.

## __AWS Elemental MediaLive__
  - ### Features
    - Exposing TileMedia H265 options

## __AWS Global Accelerator__
  - ### Features
    - AWS Global Accelerator now supports cross-account sharing for bring your own IP addresses.

## __AWS SDK for Java v2__
  - ### Features
    - Support creating an `SdkPublisher` from an `Iterable`
    - Updated endpoint and partition metadata.

## __Amazon EC2 Container Service__
  - ### Features
    - Documentation only update for Amazon ECS.

## __Amazon EMR Containers__
  - ### Features
    - This release increases the number of supported job template parameters from 20 to 100.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Added support for ModifyInstanceMetadataDefaults and GetInstanceMetadataDefaults to set Instance Metadata Service account defaults

## __Amazon SageMaker Service__
  - ### Features
    - Introduced support for the following new instance types on SageMaker Studio for JupyterLab and CodeEditor applications: m6i, m6id, m7i, c6i, c6id, c7i, r6i, r6id, r7i, and p5

# __2.25.16__ __2024-03-22__
## __AWS Price List Service__
  - ### Features
    - Add ResourceNotFoundException to ListPriceLists and GetPriceListFileUrl APIs

## __AWS SDK for Java v2__
  - ### Bugfixes
    - Improve HTTP2/PING timeout logic. Improvement avoids premature timeouts due to delays in scheduling the write of PING frame to the channel and/or flushing it to the socket.
        - Contributed by: [@akidambisrinivasan](https://github.com/akidambisrinivasan)

## __AWS SecurityHub__
  - ### Features
    - Added new resource detail object to ASFF, including resource for LastKnownExploitAt

## __AWSKendraFrontendService__
  - ### Features
    - Documentation update, March 2024. Corrects some docs for Amazon Kendra.

## __Amazon Kinesis Firehose__
  - ### Features
    - Updates Amazon Firehose documentation for message regarding Enforcing Tags IAM Policy.

## __IAM Roles Anywhere__
  - ### Features
    - This release relaxes constraints on the durationSeconds request parameter for the *Profile APIs that support it. This parameter can now take on values that go up to 43200.

## __Contributors__
Special thanks to the following contributors to this release: 

[@akidambisrinivasan](https://github.com/akidambisrinivasan)
# __2.25.15__ __2024-03-21__
## __CodeArtifact__
  - ### Features
    - This release adds Package groups to CodeArtifact so you can more conveniently configure package origin controls for multiple packages.

## __S3 Transfer Manager__
  - ### Bugfixes
    - Fix normalize key method to not strip extra character when prefix is not immediately followed by separator in the key.
    - Fixed OOM issue that could surface when users try to download objects from an S3 bucket that has millions of small files. See [#4987](https://github.com/aws/aws-sdk-java-v2/issues/4987)

# __2.25.14__ __2024-03-20__
## __AWS CodeBuild__
  - ### Features
    - This release adds support for new webhook events (RELEASED and PRERELEASED) and filter types (TAG_NAME and RELEASE_NAME).

## __AWS Savings Plans__
  - ### Features
    - Introducing the Savings Plans Return feature enabling customers to return their Savings Plans within 7 days of purchase.

## __Access Analyzer__
  - ### Features
    - This release adds support for policy validation and external access findings for DynamoDB tables and streams. IAM Access Analyzer helps you author functional and secure resource-based policies and identify cross-account access. Updated service API, documentation, and paginators.

## __Amazon Connect Service__
  - ### Features
    - This release updates the *InstanceStorageConfig APIs to support a new ResourceType: REAL_TIME_CONTACT_ANALYSIS_CHAT_SEGMENTS. Use this resource type to enable streaming for real-time analysis of chat contacts and to associate a Kinesis stream where real-time analysis chat segments will be published.

## __Amazon DynamoDB__
  - ### Features
    - This release introduces 3 new APIs ('GetResourcePolicy', 'PutResourcePolicy' and 'DeleteResourcePolicy') and modifies the existing 'CreateTable' API for the resource-based policy support. It also modifies several APIs to accept a 'TableArn' for the 'TableName' parameter.

## __Amazon Managed Blockchain Query__
  - ### Features
    - AMB Query: update GetTransaction to include transactionId as input

# __2.25.13__ __2024-03-19__
## __AWS CloudFormation__
  - ### Features
    - Documentation update, March 2024. Corrects some formatting.

## __Amazon CloudWatch Logs__
  - ### Features
    - Update LogSamples field in Anomaly model to be a list of LogEvent

## __Amazon Elastic Compute Cloud__
  - ### Features
    - This release adds the new DescribeMacHosts API operation for getting information about EC2 Mac Dedicated Hosts. Users can now see the latest macOS versions that their underlying Apple Mac can support without needing to be updated.

## __Amazon Managed Blockchain Query__
  - ### Features
    - Introduces a new API for Amazon Managed Blockchain Query: ListFilteredTransactionEvents.

## __FinSpace User Environment Management service__
  - ### Features
    - Adding new attributes readWrite and onDemand to dataview models for Database Maintenance operations.

# __2.25.12__ __2024-03-18__
## __AWS CloudFormation__
  - ### Features
    - This release supports for a new API ListStackSetAutoDeploymentTargets, which provider auto-deployment configuration as a describable resource. Customers can now view the specific combinations of regions and OUs that are being auto-deployed.

## __AWS Key Management Service__
  - ### Features
    - Adds the ability to use the default policy name by omitting the policyName parameter in calls to PutKeyPolicy and GetKeyPolicy

## __AWS MediaTailor__
  - ### Features
    - This release adds support to allow customers to show different content within a channel depending on metadata associated with the viewer.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __Amazon Relational Database Service__
  - ### Features
    - This release launches the ModifyIntegration API and support for data filtering for zero-ETL Integrations.

## __Amazon Simple Storage Service__
  - ### Features
    - Fix two issues with response root node names.

## __Amazon Timestream Query__
  - ### Features
    - Documentation updates, March 2024

# __2.25.11__ __2024-03-15__
## __AWS Backup__
  - ### Features
    - This release introduces a boolean attribute ManagedByAWSBackupOnly as part of ListRecoveryPointsByResource api to filter the recovery points based on ownership. This attribute can be used to filter out the recovery points protected by AWSBackup.

## __AWS CodeBuild__
  - ### Features
    - AWS CodeBuild now supports overflow behavior on Reserved Capacity.

## __AWS SDK for Java v2__
  - ### Features
    - Add the model for S3 Event Notifications and json parsers for them
    - Updated endpoint and partition metadata.

## __Amazon Connect Service__
  - ### Features
    - This release adds Hierarchy based Access Control fields to Security Profile public APIs and adds support for UserAttributeFilter to SearchUsers API.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Add media accelerator and neuron device information on the describe instance types API.

## __Amazon Kinesis Analytics__
  - ### Features
    - Support for Flink 1.18 in Managed Service for Apache Flink

## __Amazon SageMaker Service__
  - ### Features
    - Adds m6i, m6id, m7i, c6i, c6id, c7i, r6i r6id, r7i, p5 instance type support to Sagemaker Notebook Instances and miscellaneous wording fixes for previous Sagemaker documentation.

## __Amazon Simple Storage Service__
  - ### Features
    - Documentation updates for Amazon S3.

## __Amazon WorkSpaces Thin Client__
  - ### Features
    - Removed unused parameter kmsKeyArn from UpdateDeviceRequest

# __2.25.10__ __2024-03-14__
## __AWS Amplify__
  - ### Features
    - Documentation updates for Amplify. Identifies the APIs available only to apps created using Amplify Gen 1.

## __AWS EC2 Instance Connect__
  - ### Features
    - This release includes a new exception type "SerialConsoleSessionUnsupportedException" for SendSerialConsoleSSHPublicKey API.

## __AWS Fault Injection Simulator__
  - ### Features
    - This release adds support for previewing target resources before running a FIS experiment. It also adds resource ARNs for actions, experiments, and experiment templates to API responses.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

  - ### Bugfixes
    - Fixed an issue in `ByteBufferStoringSubscriber` where it could buffer more data than configured, resulting in out of memory error. See [#4999](https://github.com/aws/aws-sdk-java-v2/issues/4999).

## __AWS Secrets Manager__
  - ### Features
    - Doc only update for Secrets Manager

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for EBCDIC collation for RDS for Db2.

## __Elastic Load Balancing__
  - ### Features
    - This release allows you to configure HTTP client keep-alive duration for communication between clients and Application Load Balancers.

## __Timestream InfluxDB__
  - ### Features
    - This is the initial SDK release for Amazon Timestream for InfluxDB. Amazon Timestream for InfluxDB is a new time-series database engine that makes it easy for application developers and DevOps teams to run InfluxDB databases on AWS for near real-time time-series applications using open source APIs.

# __2.25.9__ __2024-03-13__
## __Amazon Interactive Video Service RealTime__
  - ### Features
    - adds support for multiple new composition layout configuration options (grid, pip)

## __Amazon Kinesis Analytics__
  - ### Features
    - Support new RuntimeEnvironmentUpdate parameter within UpdateApplication API allowing callers to change the Flink version upon which their application runs.

## __Amazon Simple Storage Service__
  - ### Features
    - This release makes the default option for S3 on Outposts request signing to use the SigV4A algorithm when using AWS Common Runtime (CRT).

## __S3 Transfer Manager__
  - ### Bugfixes
    - AWS-CRT based S3 Transfer Manager now relies on CRT to perform file reading for upload directory. Related to [#4999](https://github.com/aws/aws-sdk-java-v2/issues/4999)

# __2.25.8__ __2024-03-12__
## __AWS CloudFormation__
  - ### Features
    - CloudFormation documentation update for March, 2024

## __AWS SDK for Java v2__
  - ### Features
    - Allow users to configure `subscribeTimeout` for BlockingOutputStreamAsyncRequestBody. See [#4893](https://github.com/aws/aws-sdk-java-v2/issues/4893)

## __Amazon Connect Service__
  - ### Features
    - This release increases MaxResults limit to 500 in request for SearchUsers, SearchQueues and SearchRoutingProfiles APIs of Amazon Connect.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - Documentation updates for Amazon EC2.

## __Amazon Simple Systems Manager (SSM)__
  - ### Features
    - March 2024 doc-only updates for Systems Manager.

## __Managed Streaming for Kafka__
  - ### Features
    - Added support for specifying the starting position of topic replication in MSK-Replicator.

# __2.25.7__ __2024-03-11__
## __AWS CodeStar connections__
  - ### Features
    - Added a sync configuration enum to disable publishing of deployment status to source providers (PublishDeploymentStatus). Added a sync configuration enum (TriggerStackUpdateOn) to only trigger changes.

## __AWS Elemental MediaPackage v2__
  - ### Features
    - This release enables customers to safely update their MediaPackage v2 channel groups, channels and origin endpoints using entity tags.

## __Amazon ElastiCache__
  - ### Features
    - Revisions to API text that are now to be carried over to SDK text, changing usages of "SFO" in code examples to "us-west-1", and some other typos.

# __2.25.6__ __2024-03-08__
## __AWS Batch__
  - ### Features
    - This release adds JobStateTimeLimitActions setting to the Job Queue API. It allows you to configure an action Batch can take for a blocking job in front of the queue after the defined period of time. The new parameter applies for ECS, EKS, and FARGATE Job Queues.

## __AWS CloudTrail__
  - ### Features
    - Added exceptions to CreateTrail, DescribeTrails, and ListImportFailures APIs.

## __AWS CodeBuild__
  - ### Features
    - This release adds support for a new webhook event: PULL_REQUEST_CLOSED.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

## __AWS Transfer Family__
  - ### Features
    - Added DES_EDE3_CBC to the list of supported encryption algorithms for messages sent with an AS2 connector.

## __Agents for Amazon Bedrock Runtime__
  - ### Features
    - Documentation update for Bedrock Runtime Agent

## __Amazon Cognito Identity Provider__
  - ### Features
    - Add ConcurrentModificationException to SetUserPoolMfaConfig

## __Amazon GuardDuty__
  - ### Features
    - Add RDS Provisioned and Serverless Usage types

# __2.25.5__ __2024-03-07__
## __AWS Lambda__
  - ### Features
    - Documentation updates for AWS Lambda

## __AWS S3__
  - ### Bugfixes
    - Fixed the issue in S3 multipart client where the list of parts could be out of order in CompleteMultipartRequest, causing `The list of parts was not in ascending order` error to be thrown.

## __AWS SDK for Java v2__
  - ### Bugfixes
    - Modify ARN toString to print a valid ARN when there's no region or acountId
        - Contributed by: [@Madrigal](https://github.com/Madrigal)

## __AWS WAFV2__
  - ### Features
    - You can increase the max request body inspection size for some regional resources. The size setting is in the web ACL association config. Also, the AWSManagedRulesBotControlRuleSet EnableMachineLearning setting now takes a Boolean instead of a primitive boolean type, for languages like Java.

## __Amazon AppConfig__
  - ### Features
    - AWS AppConfig now supports dynamic parameters, which enhance the functionality of AppConfig Extensions by allowing you to provide parameter values to your Extensions at the time you deploy your configuration.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - This release adds an optional parameter to RegisterImage and CopyImage APIs to support tagging AMIs at the time of creation.

## __Amazon Import/Export Snowball__
  - ### Features
    - Doc-only update for change to EKS-Anywhere ordering.

## __Amazon Managed Grafana__
  - ### Features
    - Adds support for the new GrafanaToken as part of the Amazon Managed Grafana Enterprise plugins upgrade to associate your AWS account with a Grafana Labs account.

## __Amazon Relational Database Service__
  - ### Features
    - Updates Amazon RDS documentation for io2 storage for Multi-AZ DB clusters

## __Amazon WorkSpaces__
  - ### Features
    - Added note for user decoupling

## __Payment Cryptography Data Plane__
  - ### Features
    - AWS Payment Cryptography EMV Decrypt Feature Release

## __Contributors__
Special thanks to the following contributors to this release: 

[@Madrigal](https://github.com/Madrigal)
# __2.25.4__ __2024-03-06__
## __Amazon DynamoDB__
  - ### Features
    - Doc only updates for DynamoDB documentation

## __Amazon Redshift__
  - ### Features
    - Update for documentation only. Covers port ranges, definition updates for data sharing, and definition updates to cluster-snapshot documentation.

## __Amazon Relational Database Service__
  - ### Features
    - Updated the input of CreateDBCluster and ModifyDBCluster to support setting CA certificates. Updated the output of DescribeDBCluster to show current CA certificate setting value.

## __Amazon Verified Permissions__
  - ### Features
    - Deprecating details in favor of configuration for GetIdentitySource and ListIdentitySources APIs.

## __AmazonMWAA__
  - ### Features
    - Amazon MWAA adds support for Apache Airflow v2.8.1.

## __EC2 Image Builder__
  - ### Features
    - Add PENDING status to Lifecycle Execution resource status. Add StartTime and EndTime to ListLifecycleExecutionResource API response.

# __2.25.3__ __2024-03-05__
## __AWS Chatbot__
  - ### Features
    - Minor update to documentation.

## __AWS Organizations__
  - ### Features
    - This release contains an endpoint addition

## __Amazon API Gateway__
  - ### Features
    - Documentation updates for Amazon API Gateway

## __Amazon Simple Email Service__
  - ### Features
    - Adds support for providing custom headers within SendEmail and SendBulkEmail for SESv2.

# __2.25.2__ __2024-03-04__
## __AWS CloudFormation__
  - ### Features
    - Add DetailedStatus field to DescribeStackEvents and DescribeStacks APIs

## __AWS Organizations__
  - ### Features
    - Documentation update for AWS Organizations

## __Amazon FSx__
  - ### Features
    - Added support for creating FSx for NetApp ONTAP file systems with up to 12 HA pairs, delivering up to 72 GB/s of read throughput and 12 GB/s of write throughput.

# __2.25.1__ __2024-03-01__
## __Access Analyzer__
  - ### Features
    - Fixed a typo in description field.

## __Amazon Elastic Compute Cloud__
  - ### Features
    - With this release, Amazon EC2 Auto Scaling groups, EC2 Fleet, and Spot Fleet improve the default price protection behavior of attribute-based instance type selection of Spot Instances, to consistently select from a wide range of instance types.

## __Auto Scaling__
  - ### Features
    - With this release, Amazon EC2 Auto Scaling groups, EC2 Fleet, and Spot Fleet improve the default price protection behavior of attribute-based instance type selection of Spot Instances, to consistently select from a wide range of instance types.

# __2.25.0__ __2024-02-29__
## __AWS CRT HTTP Client__
  - ### Features
    - Support Non proxy host settings in the ProxyConfiguration for Crt http client.

  - ### Bugfixes
    - Addressing Issue [#4745](https://github.com/aws/aws-sdk-java-v2/issues/4745) , Netty and CRT clients' default proxy settings have been made consistent with the Apache client, now using environment and system property settings by default.\n To disable the use of environment variables and system properties by default, set useSystemPropertyValue(false) and useEnvironmentVariablesValues(false) in ProxyConfigurations.

## __AWS Migration Hub Orchestrator__
  - ### Features
    - Adds new CreateTemplate, UpdateTemplate and DeleteTemplate APIs.

## __AWS SDK for Java v2__
  - ### Features
    - Updated endpoint and partition metadata.

  - ### Bugfixes
    - Addressing Issue [#4745](https://github.com/aws/aws-sdk-java-v2/issues/4745) , Netty and CRT clients' default proxy settings have been made consistent with the Apache client, now using environment and system property settings by default.
       To disable the use of environment variables and system properties by default, set useSystemPropertyValue(false) and useEnvironmentVariablesValues(false) in ProxyConfigurations.

## __Amazon DocumentDB Elastic Clusters__
  - ### Features
    - Launched Elastic Clusters Readable Secondaries, Start/Stop, Configurable Shard Instance count, Automatic Backups and Snapshot Copying

## __Amazon Elastic Kubernetes Service__
  - ### Features
    - Added support for new AL2023 AMIs to the supported AMITypes.

## __Amazon Lex Model Building V2__
  - ### Features
    - This release makes AMAZON.QnAIntent generally available in Amazon Lex. This generative AI feature leverages large language models available through Amazon Bedrock to automate frequently asked questions (FAQ) experience for end-users.

## __Amazon QuickSight__
  - ### Features
    - TooltipTarget for Combo chart visuals; ColumnConfiguration limit increase to 2000; Documentation Update

## __Amazon SageMaker Service__
  - ### Features
    - Adds support for ModelDataSource in Model Packages to support unzipped models. Adds support to specify SourceUri for models which allows registration of models without mandating a container for hosting. Using SourceUri, customers can decouple the model from hosting information during registration.

## __Amazon Security Lake__
  - ### Features
    - Add capability to update the Data Lake's MetaStoreManager Role in order to perform required data lake updates to use Iceberg table format in their data lake or update the role for any other reason.

## __Netty NIO Async HTTP Client__
  - ### Bugfixes
    - Addressing Issue [#4745](https://github.com/aws/aws-sdk-java-v2/issues/4745) , Netty and CRT clients' default proxy settings have been made consistent with the Apache client, now using environment and system property settings by default.\n To disable the use of environment variables and system properties by default, set useSystemPropertyValue(false) and useEnvironmentVariablesValues(false) in ProxyConfigurations

