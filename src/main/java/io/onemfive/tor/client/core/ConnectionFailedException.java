package io.onemfive.tor.client.core;

public class ConnectionFailedException extends ConnectionIOException {

	private static final long serialVersionUID = -4484347156587613574L;

	public ConnectionFailedException(String message) {
		super(message);
	}

}
