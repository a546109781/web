package cc.nanjo.common.bilibili.black.entity;

import com.alibaba.fastjson.JSON;

public final class Response<T> {

	private T data;
	private int code;
	private long count;
	private String msg;

	public Response(T data, long count){
		this.data = (T) JSON.toJSON(data);
		this.count = count;
		if(count==0){
			this.code=0;
			this.msg="no";
		}else {
			this.code=200;
			this.msg="ok";
		}
	}

	@Override
	public String toString() {
		return "Response{" +
				"data=" + data +
				", code=" + code +
				", count=" + count +
				", msg='" + msg + '\'' +
				'}';
	}

	public T getData() {
		return data;
	}

	public int getCode() {
		return code;
	}


	public long getCount() {
		return count;
	}


	public String getMsg() {
		return msg;
	}

}
