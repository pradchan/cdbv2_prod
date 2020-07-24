connect('weblogic','Oracle123!','t3://localhost:7101')
edit()
startEdit()

cd('/')
cmo.createJDBCSystemResource('datasource_spatialAnalytics')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics')
cmo.setName('datasource_spatialAnalytics')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCDataSourceParams/datasource_spatialAnalytics')
set('JNDINames',jarray.array([String('converge.oracle.spatialAnalytics')], String))

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics')
cmo.setDatasourceType('GENERIC')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCDriverParams/datasource_spatialAnalytics')
cmo.setUrl('jdbc:oracle:thin:@//localhost:1521/apppdb')
cmo.setDriverName('oracle.jdbc.OracleDriver')
cmo.setPassword('Oracle_4U')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCConnectionPoolParams/datasource_spatialAnalytics')
cmo.setTestTableName('SQL ISVALID\r\n\r\n')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCDriverParams/datasource_spatialAnalytics/Properties/datasource_spatialAnalytics')
cmo.createProperty('user')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCDriverParams/datasource_spatialAnalytics/Properties/datasource_spatialAnalytics/Properties/user')
cmo.setValue('analytics')

cd('/JDBCSystemResources/datasource_spatialAnalytics/JDBCResource/datasource_spatialAnalytics/JDBCDataSourceParams/datasource_spatialAnalytics')
cmo.setGlobalTransactionsProtocol('OnePhaseCommit')

cd('/JDBCSystemResources/datasource_spatialAnalytics')
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

save()

activate()

exit()
