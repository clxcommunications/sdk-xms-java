package com.clxcommunications.xms;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@ValueStylePackageDirect
@JsonSerialize(using = JacksonUtils.BatchIdSerializer.class)
@JsonDeserialize(using = JacksonUtils.BatchIdDeserializer.class)
public abstract class BatchId {

	public abstract String id();

	public static BatchId of(String id) {
		return ImmutableBatchId.of(id);
	}

}