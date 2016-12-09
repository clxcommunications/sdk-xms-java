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

import java.util.List;

import javax.annotation.Nonnull;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A page within a paged inbound messages listing.
 */
@Value.Immutable
@ValueStylePackage
@JsonDeserialize(builder = PagedInboundsResult.Builder.class)
public abstract class PagedInboundsResult extends Page<MoSms> {

	/**
	 * A builder of inbound messages result pages.
	 */
	public static class Builder extends PagedInboundsResultImpl.Builder {

		Builder() {
		}

	}

	/**
	 * Creates a builder of {@link PagedInboundsResult} instances.
	 * 
	 * @return a builder
	 */
	@Nonnull
	public static final PagedInboundsResult.Builder builder() {
		return new Builder();
	}

	@JsonProperty("inbounds")
	@Override
	public abstract List<MoSms> content();

}
