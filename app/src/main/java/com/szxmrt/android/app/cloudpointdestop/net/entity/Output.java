package com.szxmrt.android.app.cloudpointdestop.net.entity;

public class Output {
	private OutputType mOutputType;
	private ApkData mApkData;
	private String mPath;

	public OutputType getOutputType() {
		return mOutputType;
	}

	public void setOutputType(OutputType outputType) {
		mOutputType = outputType;
	}

	public ApkData getApkData() {
		return mApkData;
	}

	public void setApkData(ApkData apkData) {
		mApkData = apkData;
	}

	public String getPath() {
		return mPath;
	}

	public void setPath(String path) {
		mPath = path;
	}

	@Override
	public String toString() {
		return "Output{" +
				"mOutputType=" + mOutputType +
				", mApkData=" + mApkData +
				", mPath='" + mPath + '\'' +
				'}';
	}

	public class OutputType{
		private String mType;

		public String getType() {
			return mType;
		}

		public void setType(String type) {
			mType = type;
		}

		@Override
		public String toString() {
			return "OutputType{" +
					"mType='" + mType + '\'' +
					'}';
		}
	}

	public class ApkData{
		private String mType;
		private int mVersionCode;
		private String mVersionName;
		private boolean mEnabled;
		private String mOutputFile;
		private String mFullName;
		private String mBaseName;

		public String getType() {
			return mType;
		}

		public void setType(String type) {
			this.mType = type;
		}

		public int getVersionCode() {
			return mVersionCode;
		}

		public void setVersionCode(int versionCode) {
			this.mVersionCode = versionCode;
		}

		public String getVersionName() {
			return mVersionName;
		}

		public void setVersionName(String versionName) {
			this.mVersionName = versionName;
		}

		public boolean isEnabled() {
			return mEnabled;
		}

		public void setEnabled(boolean enabled) {
			this.mEnabled = enabled;
		}

		public String getOutputFile() {
			return mOutputFile;
		}

		public void setOutputFile(String outputFile) {
			this.mOutputFile = outputFile;
		}

		public String getFullName() {
			return mFullName;
		}

		public void setFullName(String fullName) {
			this.mFullName = fullName;
		}

		public String getBaseName() {
			return mBaseName;
		}

		public void setBaseName(String baseName) {
			this.mBaseName = baseName;
		}
	}

	/*

	[
  {
    "outputType": {
      "mType": "APK"
    },
    "apkData": {
      "mType": "MAIN",
      "splits": [],
      "mVersionCode": 1,
      "mVersionName": "1.0.0",
      "mEnabled": true,
      "mOutputFile": "app-release.apk",
      "mFullName": "release",
      "mBaseName": "release"
    },
    "path": "app-release.apk",
    "properties": {}
  }
]

	*/
}
