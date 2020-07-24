connect('weblogic','Oracle123!','t3://localhost:7101')
edit()
startEdit()

cd('/')
cmo.createJDBCSystemResource('datasource_jsonXml')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml')
cmo.setName('datasource_jsonXml')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCDataSourceParams/datasource_jsonXml')
set('JNDINames',jarray.array([String('converge.oracle.jsonxml')], String))

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml')
cmo.setDatasourceType('GENERIC')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCDriverParams/datasource_jsonXml')
cmo.setUrl('jdbc:oracle:thin:@//localhost:1521/apppdb')
cmo.setDriverName('oracle.jdbc.OracleDriver')
cmo.setPassword('Oracle_4U')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCConnectionPoolParams/datasource_jsonXml')
cmo.setTestTableName('SQL ISVALID\r\n\r\n')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCDriverParams/datasource_jsonXml/Properties/datasource_jsonXml')
cmo.createProperty('user')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCDriverParams/datasource_jsonXml/Properties/datasource_jsonXml/Properties/user')
cmo.setValue('appnodejs')

cd('/JDBCSystemResources/datasource_jsonXml/JDBCResource/datasource_jsonXml/JDBCDataSourceParams/datasource_jsonXml')
cmo.setGlobalTransactionsProtocol('OnePhaseCommit')

cd('/JDBCSystemResources/datasource_jsonXml')
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

save()

activate()

exit()
