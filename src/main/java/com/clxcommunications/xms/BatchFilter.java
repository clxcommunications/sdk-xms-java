package com.clxcommunications.xms;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.immutables.value.Value;
import org.threeten.bp.LocalDate;

@Value.Immutable
@ValueStylePublic
public abstract class BatchFilter {

	/**
	 * The requested number of entries per page. A non-positive value means that
	 * the default value will be used.
	 * 
	 * @return the desired page size
	 */
	@Value.Default
	public int pageSize() {
		return 0;
	}

	@Nullable
	public abstract LocalDate startDate();

	@Nullable
	public abstract LocalDate endDate();

	public abstract Set<String> from();

	public abstract Set<String> tags();

	protected void check() {
		for (String s : from()) {
			if (s.contains(",")) {
				throw new IllegalArgumentException("from contains comma");
			}
		}

		for (String s : tags()) {
			if (s.contains(",")) {
				throw new IllegalArgumentException("tags contains comma");
			}
		}
	}

	/**
	 * Formats this filter as an URL encoded list of query parameters.
	 * 
	 * @param page
	 *            the page to request
	 * @return a non-null string containing query parameters
	 */
	@Nonnull
	String toUrlEncodedQuery(int page) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(6);

		params.add(new BasicNameValuePair("page", String.valueOf(page)));

		if (pageSize() > 0) {
			params.add(new BasicNameValuePair("page_size",
			        String.valueOf(pageSize())));
		}

		if (startDate() != null) {
			params.add(new BasicNameValuePair("start_date",
			        startDate().toString()));
		}

		if (endDate() != null) {
			params.add(new BasicNameValuePair("end_date",
			        endDate().toString()));
		}

		if (!from().isEmpty()) {
			params.add(new BasicNameValuePair("from",
			        Utils.join(",", from())));
		}

		if (!tags().isEmpty()) {
			params.add(new BasicNameValuePair("tags",
			        Utils.join(",", tags())));
		}

		return URLEncodedUtils.format(params, Consts.UTF_8);
	}

}
