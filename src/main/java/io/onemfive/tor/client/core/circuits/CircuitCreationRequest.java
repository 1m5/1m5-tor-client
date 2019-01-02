package io.onemfive.tor.client.core.circuits;

import java.util.Collections;
import java.util.List;

import io.onemfive.tor.client.core.Circuit;
import io.onemfive.tor.client.core.CircuitBuildHandler;
import io.onemfive.tor.client.core.CircuitNode;
import io.onemfive.tor.client.core.Connection;
import io.onemfive.tor.client.core.Router;
import io.onemfive.tor.client.core.circuits.path.CircuitPathChooser;
import io.onemfive.tor.client.core.circuits.path.PathSelectionFailedException;
import io.onemfive.tor.client.core.*;

public class CircuitCreationRequest implements CircuitBuildHandler {
	private final CircuitImpl circuit;
	private final CircuitPathChooser pathChooser;
	private final CircuitBuildHandler buildHandler;
	private final boolean isDirectoryCircuit;
	
	private List<Router> path;
	
	public CircuitCreationRequest(CircuitPathChooser pathChooser, Circuit circuit, CircuitBuildHandler buildHandler, boolean isDirectoryCircuit) {
		this.pathChooser = pathChooser;
		this.circuit = (CircuitImpl) circuit;
		this.buildHandler = buildHandler;
		this.path = Collections.emptyList();
		this.isDirectoryCircuit = isDirectoryCircuit;
	}
	
	void choosePath() throws InterruptedException, PathSelectionFailedException {
		if(!(circuit instanceof CircuitImpl)) {
			throw new IllegalArgumentException();
		}
		path = ((CircuitImpl)circuit).choosePath(pathChooser);

	}

	CircuitImpl getCircuit() {
		return circuit;
	}

	List<Router> getPath() {
		return path;
	}
	
	int getPathLength() {
		return path.size();
	}
	
	Router getPathElement(int idx) {
		return path.get(idx);
	}
	
	CircuitBuildHandler getBuildHandler() {
		return buildHandler;
	}
	
	boolean isDirectoryCircuit() {
		return isDirectoryCircuit;
	}

	public void connectionCompleted(Connection connection) {
		if(buildHandler != null) {
			buildHandler.connectionCompleted(connection);
		}
	}

	public void connectionFailed(String reason) {
		if(buildHandler != null) {
			buildHandler.connectionFailed(reason);
		}
	}

	public void nodeAdded(CircuitNode node) {
		if(buildHandler != null) {
			buildHandler.nodeAdded(node);
		}
	}

	public void circuitBuildCompleted(Circuit circuit) {
		if(buildHandler != null) {
			buildHandler.circuitBuildCompleted(circuit);
		}
	}

	public void circuitBuildFailed(String reason) {
		if(buildHandler != null) {
			buildHandler.circuitBuildFailed(reason);
		}
	}
}
