package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.StartDocument;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
	private boolean result;
	private String message;
	private D data;

	/** 성공 응답 */
	public static <D> ResponseDto<D> setSuccess(String message, D data) {
		return ResponseDto.set(true, message, data);
	}

	/** 실패 응답 – 데이터도 함께 전달할 때 */
	public static <D> ResponseDto<D> setFailed(String message, D data) {
		return ResponseDto.set(false, message, data); // ← data 넘겨줌
	}

	/** 실패 응답 – 메시지만 보낼 때 */
	public static <D> ResponseDto<D> setFailed(String message) {
		return ResponseDto.set(false, message, null);
	}
}