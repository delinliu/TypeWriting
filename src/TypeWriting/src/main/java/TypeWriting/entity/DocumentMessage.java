package TypeWriting.entity;

public class DocumentMessage {

	private String type;
	private int offset;
	private int length;
	private String text;
	private long timestamp;

	public DocumentMessage(String type, int offset, int length, String text,
			long timestamp) {
		super();
		this.type = type;
		this.offset = offset;
		this.length = length;
		this.text = text;
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "【" + type + "】" + offset + " " + length + " : " + text;
	}
}
