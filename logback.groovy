import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.pattern.SyslogStartConverter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.encoder.LogstashEncoder

import static ch.qos.logback.classic.Level.*

def appenderList = ["CONSOLE", "logstash"]

//tags
def RSYSLOG_TAG = "%syslogStart{USER}"
//

def rHost = "localhost"
def rPort = 514

def DEFAULT_LINE_LENGTH = 80

println "=" * DEFAULT_LINE_LENGTH
println """
    LOGGING FILE      : logback.groovy
"""
println "=" * DEFAULT_LINE_LENGTH


appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-4relative %d %-5level [ %t ] %-55logger{13} | %m %n"
    }
}


conversionRule("syslogStart", SyslogStartConverter)

appender("logstash", LogstashTcpSocketAppender) {
    remoteHost = rHost
    port = rPort
    encoder(LogstashEncoder) {
        prefix(LayoutWrappingEncoder) {
            layout(PatternLayout) {
                pattern = RSYSLOG_TAG
            }
        }
    }
}

root(INFO, appenderList)
