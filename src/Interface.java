public class Interface {
	String interfaceName = "";
	String adminStatus = "";
	String operationStatus = "";
	String macAddress = "";
	String description = "";
	short mtu;
	String ipAddress = "";
	String duplexMode = "";
	String ifSpeed = "";

	public Interface() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Interface(String interfaceName, String adminStatus,
			String operationStatus, String macAddress, String description,
			short mtu, String ipAddress, String duplexMode, String ifSpeed) {
		super();
		this.interfaceName = interfaceName;
		this.adminStatus = adminStatus;
		this.operationStatus = operationStatus;
		this.macAddress = macAddress;
		this.description = description;
		this.mtu = mtu;
		this.ipAddress = ipAddress;
		this.duplexMode = duplexMode;
		this.ifSpeed = ifSpeed;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String isAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getMtu() {
		return mtu;
	}

	public void setMtu(short mtu) {
		this.mtu = mtu;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDuplexMode() {
		return duplexMode;
	}

	public void setDuplexMode(String duplexMode) {
		this.duplexMode = duplexMode;
	}

	public String getIfSpeed() {
		return ifSpeed;
	}

	public void setIfSpeed(String ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	@Override
	public String toString() {
		return "[\n interfaceName:" + interfaceName + "\n adminStatus:"
				+ adminStatus + "\n operationStatus:" + operationStatus
				+ "\n macAddress:" + macAddress + "\n description:"
				+ description + "\n mtu:" + mtu + "\n ipAddress:" + ipAddress
				+ "\n duplexMode:" + duplexMode + "\n ifSpeed:" + ifSpeed
				+ "\n]\n";
	}

}
