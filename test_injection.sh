mvn surefire:test -Dtest=KylinConfigTest#testMRConfigOverride -pl core-common -Dconfig.inject.cli="kylin.test.bcc.old.key=2"
