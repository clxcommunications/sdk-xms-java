package com.clxcommunications.xms.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.clxcommunications.testsupport.TestUtils;
import com.clxcommunications.xms.ApiObjectMapper;
import com.clxcommunications.xms.Utils;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class BatchDeliveryReportTest {

	private final ApiObjectMapper json = new ApiObjectMapper();

	@Test
	public void canSerialize() throws Exception {
		BatchDeliveryReport input = BatchDeliveryReportImpl.builder()
		        .batchId(BatchId.of("batchid"))
		        .totalMessageCount(50)
		        .addStatus(BatchDeliveryReportImpl.Status.builder()
		                .code(10)
		                .status(DeliveryStatus.DELIVERED)
		                .count(20)
		                .build())
		        .addStatus(BatchDeliveryReportImpl.Status.builder()
		                .code(20)
		                .status(DeliveryStatus.FAILED)
		                .count(30)
		                .build())
		        .build();

		String expected = Utils.join("\n",
		        "{",
		        "  'batch_id': 'batchid',",
		        "  'total_message_count': 50,",
		        "  'type': 'delivery_report_sms',",
		        "  'statuses': [",
		        "    {",
		        "      'code': 10,",
		        "      'status': 'Delivered',",
		        "      'count': 20",
		        "    },",
		        "    {",
		        "      'code': 20,",
		        "      'status': 'Failed',",
		        "      'count': 30",
		        "    }",
		        "  ]",
		        "}").replace('\'', '"');

		String actual = json.writeValueAsString(input);

		assertThat(actual, is(TestUtils.jsonEqualTo(expected)));
	}

	@Test
	public void canDeserialize() throws Exception {
		BatchDeliveryReport expected = BatchDeliveryReportImpl.builder()
		        .batchId(BatchId.of("batchid"))
		        .totalMessageCount(50)
		        .addStatus(BatchDeliveryReportImpl.Status.builder()
		                .code(10)
		                .status(DeliveryStatus.DELIVERED)
		                .count(20)
		                .build())
		        .addStatus(BatchDeliveryReportImpl.Status.builder()
		                .code(20)
		                .status(DeliveryStatus.FAILED)
		                .count(30)
		                .build())
		        .build();

		String input = json.writeValueAsString(expected);

		BatchDeliveryReport actual =
		        json.readValue(input, BatchDeliveryReport.class);

		assertThat(actual, is(expected));
	}

}