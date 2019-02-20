package com.hackerrank.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.hackerrank.inventory.dto.InventoryDTO;
import com.hackerrank.inventory.listener.JobCompletionNotificationListener;
import com.hackerrank.inventory.step.AnimeProcessor;

@EnableBatchProcessing
@Configuration
public class CsvFileToDatabaseConfig {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
    // begin reader, writer, and processor

    
    @Bean
    public FlatFileItemReader<InventoryDTO> csvAnimeReader(){
        FlatFileItemReader<InventoryDTO> reader = new FlatFileItemReader<InventoryDTO>();
        reader.setResource(new ClassPathResource("animescsv.csv"));
        reader.setLineMapper(new DefaultLineMapper<InventoryDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "skuId", "productName", "productLabel","minQtyReq","inventoryOnHand","price" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<InventoryDTO>() {{
                setTargetType(InventoryDTO.class);
            }});
        }});
        return reader;
    }


	@Bean
	ItemProcessor<InventoryDTO, InventoryDTO> csvAnimeProcessor() {
		return new AnimeProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<InventoryDTO> csvAnimeWriter() {
		 JdbcBatchItemWriter<InventoryDTO> csvAnimeWriter = new JdbcBatchItemWriter<InventoryDTO>();
		 csvAnimeWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<InventoryDTO>());
		 csvAnimeWriter.setSql("INSERT INTO inventory (sku_id, product_name,product_label, inventory_on_hand, min_Qty_Req,price) VALUES (:skuId, :productName,:productLabel, :inventoryOnHand, :minQtyReq,:price)");
		 csvAnimeWriter.setDataSource(dataSource);
	        return csvAnimeWriter;
	}

	 // end reader, writer, and processor

    // begin job info
	@Bean
	public Step csvFileToDatabaseStep() {
		return stepBuilderFactory.get("csvFileToDatabaseStep")
				.<InventoryDTO, InventoryDTO>chunk(1)
				.reader(csvAnimeReader())
				.processor(csvAnimeProcessor())
				.writer(csvAnimeWriter())
				.build();
	}

	@Bean
	Job csvFileToDatabaseJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("csvFileToDatabaseJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(csvFileToDatabaseStep())
				.end()
				.build();
	}
	 // end job info
}
