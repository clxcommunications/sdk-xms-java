/*-
 * #%L
 * SDK for CLX XMS
 * %%
 * Copyright (C) 2016 CLX Communications
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.clxcommunications.xms.api;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.immutables.value.Value;
import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for mobile terminated batch messages. A mobile terminated message
 * can have either a {@link MtBatchTextSmsCreate textual} or a
 * {@link MtBatchBinarySmsCreate binary} message payload.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(MtBatchTextSmsCreate.class),
        @Type(MtBatchBinarySmsCreate.class)
})
public abstract class MtBatchSmsCreate {

	/**
	 * The list of message recipients. May not be empty.
	 * 
	 * @return a non-empty list of recipients
	 */
	@JsonProperty("to")
	public abstract List<String> recipients();

	/**
	 * The message originator. May be an MSISDN or short code.
	 * 
	 * @return an originator address
	 */
	@Nullable
	@JsonProperty("from")
	public abstract String sender();

	/**
	 * The type of delivery report to request for this batch.
	 * 
	 * @return a type of report or <code>null</code> to use the default type
	 */
	@Nullable
	@JsonProperty("delivery_report")
	public abstract ReportType deliveryReport();

	/**
	 * The time this batch should be sent. If <code>null</code> or set to a past
	 * time then the batch will be sent immediately.
	 * 
	 * @return the time when this batch should be sent
	 */
	@Nullable
	@JsonProperty("send_at")
	public abstract OffsetDateTime sendAt();

	/**
	 * The time at which this batch will expire. Any message not delivered by
	 * this time will be placed into an expired state and no further delivery
	 * will be attempted.
	 * 
	 * @return the time when this batch expires
	 */
	@Nullable
	@JsonProperty("expire_at")
	public abstract OffsetDateTime expireAt();
	
	/**
	 * Message will be dispatched only if it is not split to more parts than Max Number of Message Parts. 
	 * Must be higher or equal 1.
	 * 
	 * @return the max number of message parts
	 */
	@JsonProperty("max_number_of_message_parts")
	public abstract int maxNumberOfMessageParts();

	/**
	 * The URL to which batch callbacks should be sent. If <code>null</code>
	 * then callbacks will be sent to the default URL.
	 * 
	 * @return an URL having a callback listener or <code>null</code> to use the
	 *         default callback URL
	 */
	@Nullable
	@JsonProperty("callback_url")
	public abstract URI callbackUrl();

	/**
	 * The tags that should be attached to this message.
	 * 
	 * @return a non-null set of tags
	 */
	@JsonInclude(Include.NON_EMPTY)
	public abstract Set<String> tags();

	@OverridingMethodsMustInvokeSuper
	@Value.Check
	protected void check() {
		if (recipients().isEmpty()) {
			throw new IllegalStateException("no destination");
		}

		for (String to : recipients()) {
			if (to.isEmpty()) {
				throw new IllegalStateException("contains empty destination");
			}
		}

		if(maxNumberOfMessageParts() < 1) {
			throw new IllegalStateException("maxNumberOfMessages less than 1 (minimum)");
		}
		// Could comment this out for speed or could add an extra parameter that specifies if the automatic default originator should be used
//		if (sender().isEmpty()) {
//			throw new IllegalStateException("empty from address");
//		}
	}

}
