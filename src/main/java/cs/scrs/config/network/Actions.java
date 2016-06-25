package cs.scrs.config.network;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
		prefix = "network.actions",
		locations = "classpath:configurations/network.properties",
		exceptionIfInvalid = true,
		ignoreInvalidFields = false, 
		ignoreUnknownFields = false
		)
public class Actions {
	private String connect;
	private String disconnect;
	private String keepAlive;
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	public String getDisconnect() {
		return disconnect;
	}
	public void setDisconnect(String disconnect) {
		this.disconnect = disconnect;
	}
	public String getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}
	@Override
	public String toString() {
		return "Action [connect=" + connect + ", disconnect=" + disconnect + ", keepAlive=" + keepAlive + "]";
	}
	
}
