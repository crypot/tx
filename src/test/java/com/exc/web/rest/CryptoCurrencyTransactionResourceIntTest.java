package com.exc.web.rest;

import com.exc.TxApp;

import com.exc.config.SecurityBeanOverrideConfiguration;

import com.exc.service.CryptoCurrencyTransactionService;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.exc.web.rest.TestUtil.sameInstant;
import static com.exc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CryptoCurrencyTransactionResource REST controller.
 *
 * @see CryptoCurrencyTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TxApp.class})
public class CryptoCurrencyTransactionResourceIntTest {

  /*  private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TX = "AAAAAAAAAA";
    private static final String UPDATED_TX = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final Long DEFAULT_USER_INFO_ID = 1L;
    private static final Long UPDATED_USER_INFO_ID = 2L;

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final CryptoCurrencyTransactionType DEFAULT_TYPE = CryptoCurrencyTransactionType.ORDER;
    private static final CryptoCurrencyTransactionType UPDATED_TYPE = CryptoCurrencyTransactionType.OR_FEE;

    @Autowired
    private CryptoCurrencyTransactionRepository cryptoCurrencyTransactionRepository;

    @Autowired
    private CryptoCurrencyTransactionMapper cryptoCurrencyTransactionMapper;
    
    @Autowired
    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCryptoCurrencyTransactionMockMvc;

    private CryptoCurrencyTransaction cryptoCurrencyTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CryptoCurrencyTransactionResource cryptoCurrencyTransactionResource = new CryptoCurrencyTransactionResource(cryptoCurrencyTransactionService);
        this.restCryptoCurrencyTransactionMockMvc = MockMvcBuilders.standaloneSetup(cryptoCurrencyTransactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static CryptoCurrencyTransaction createEntity(EntityManager em) {
        CryptoCurrencyTransaction cryptoCurrencyTransaction = new CryptoCurrencyTransaction()
            .currencyName(DEFAULT_CURRENCY_NAME)
            .createDate(DEFAULT_CREATE_DATE)
            .tx(DEFAULT_TX)
            .message(DEFAULT_MESSAGE)
            .value(DEFAULT_VALUE)
            .userInfoId(DEFAULT_USER_INFO_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .type(DEFAULT_TYPE);
        return cryptoCurrencyTransaction;
    }

    @Before
    public void initTest() {
        cryptoCurrencyTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCryptoCurrencyTransaction() throws Exception {
        int databaseSizeBeforeCreate = cryptoCurrencyTransactionRepository.findAll().size();

        // Create the CryptoCurrencyTransaction
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO = cryptoCurrencyTransactionMapper.toDto(cryptoCurrencyTransaction);
        restCryptoCurrencyTransactionMockMvc.perform(post("/api/crypto-currency-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cryptoCurrencyTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the CryptoCurrencyTransaction in the database
        List<CryptoCurrencyTransaction> cryptoCurrencyTransactionList = cryptoCurrencyTransactionRepository.findAll();
        assertThat(cryptoCurrencyTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        CryptoCurrencyTransaction testCryptoCurrencyTransaction = cryptoCurrencyTransactionList.get(cryptoCurrencyTransactionList.size() - 1);
        assertThat(testCryptoCurrencyTransaction.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCryptoCurrencyTransaction.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testCryptoCurrencyTransaction.getTx()).isEqualTo(DEFAULT_TX);
        assertThat(testCryptoCurrencyTransaction.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCryptoCurrencyTransaction.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCryptoCurrencyTransaction.getUserInfoId()).isEqualTo(DEFAULT_USER_INFO_ID);
        assertThat(testCryptoCurrencyTransaction.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testCryptoCurrencyTransaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCryptoCurrencyTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cryptoCurrencyTransactionRepository.findAll().size();

        // Create the CryptoCurrencyTransaction with an existing ID
        cryptoCurrencyTransaction.setId(1L);
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO = cryptoCurrencyTransactionMapper.toDto(cryptoCurrencyTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCryptoCurrencyTransactionMockMvc.perform(post("/api/crypto-currency-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cryptoCurrencyTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CryptoCurrencyTransaction in the database
        List<CryptoCurrencyTransaction> cryptoCurrencyTransactionList = cryptoCurrencyTransactionRepository.findAll();
        assertThat(cryptoCurrencyTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCryptoCurrencyTransactions() throws Exception {
        // Initialize the database
        cryptoCurrencyTransactionRepository.saveAndFlush(cryptoCurrencyTransaction);

        // Get all the cryptoCurrencyTransactionList
        restCryptoCurrencyTransactionMockMvc.perform(get("/api/crypto-currency-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cryptoCurrencyTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].tx").value(hasItem(DEFAULT_TX.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].userInfoId").value(hasItem(DEFAULT_USER_INFO_ID.intValue())))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getCryptoCurrencyTransaction() throws Exception {
        // Initialize the database
        cryptoCurrencyTransactionRepository.saveAndFlush(cryptoCurrencyTransaction);

        // Get the cryptoCurrencyTransaction
        restCryptoCurrencyTransactionMockMvc.perform(get("/api/crypto-currency-transactions/{id}", cryptoCurrencyTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cryptoCurrencyTransaction.getId().intValue()))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.tx").value(DEFAULT_TX.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.userInfoId").value(DEFAULT_USER_INFO_ID.intValue()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCryptoCurrencyTransaction() throws Exception {
        // Get the cryptoCurrencyTransaction
        restCryptoCurrencyTransactionMockMvc.perform(get("/api/crypto-currency-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCryptoCurrencyTransaction() throws Exception {
        // Initialize the database
        cryptoCurrencyTransactionRepository.saveAndFlush(cryptoCurrencyTransaction);

        int databaseSizeBeforeUpdate = cryptoCurrencyTransactionRepository.findAll().size();

        // Update the cryptoCurrencyTransaction
        CryptoCurrencyTransaction updatedCryptoCurrencyTransaction = cryptoCurrencyTransactionRepository.findById(cryptoCurrencyTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedCryptoCurrencyTransaction are not directly saved in db
        em.detach(updatedCryptoCurrencyTransaction);
        updatedCryptoCurrencyTransaction
            .currencyName(UPDATED_CURRENCY_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .tx(UPDATED_TX)
            .message(UPDATED_MESSAGE)
            .value(UPDATED_VALUE)
            .userInfoId(UPDATED_USER_INFO_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .type(UPDATED_TYPE);
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO = cryptoCurrencyTransactionMapper.toDto(updatedCryptoCurrencyTransaction);

        restCryptoCurrencyTransactionMockMvc.perform(put("/api/crypto-currency-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cryptoCurrencyTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the CryptoCurrencyTransaction in the database
        List<CryptoCurrencyTransaction> cryptoCurrencyTransactionList = cryptoCurrencyTransactionRepository.findAll();
        assertThat(cryptoCurrencyTransactionList).hasSize(databaseSizeBeforeUpdate);
        CryptoCurrencyTransaction testCryptoCurrencyTransaction = cryptoCurrencyTransactionList.get(cryptoCurrencyTransactionList.size() - 1);
        assertThat(testCryptoCurrencyTransaction.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCryptoCurrencyTransaction.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testCryptoCurrencyTransaction.getTx()).isEqualTo(UPDATED_TX);
        assertThat(testCryptoCurrencyTransaction.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCryptoCurrencyTransaction.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCryptoCurrencyTransaction.getUserInfoId()).isEqualTo(UPDATED_USER_INFO_ID);
        assertThat(testCryptoCurrencyTransaction.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testCryptoCurrencyTransaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCryptoCurrencyTransaction() throws Exception {
        int databaseSizeBeforeUpdate = cryptoCurrencyTransactionRepository.findAll().size();

        // Create the CryptoCurrencyTransaction
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO = cryptoCurrencyTransactionMapper.toDto(cryptoCurrencyTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCryptoCurrencyTransactionMockMvc.perform(put("/api/crypto-currency-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cryptoCurrencyTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CryptoCurrencyTransaction in the database
        List<CryptoCurrencyTransaction> cryptoCurrencyTransactionList = cryptoCurrencyTransactionRepository.findAll();
        assertThat(cryptoCurrencyTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCryptoCurrencyTransaction() throws Exception {
        // Initialize the database
        cryptoCurrencyTransactionRepository.saveAndFlush(cryptoCurrencyTransaction);

        int databaseSizeBeforeDelete = cryptoCurrencyTransactionRepository.findAll().size();

        // Get the cryptoCurrencyTransaction
        restCryptoCurrencyTransactionMockMvc.perform(delete("/api/crypto-currency-transactions/{id}", cryptoCurrencyTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CryptoCurrencyTransaction> cryptoCurrencyTransactionList = cryptoCurrencyTransactionRepository.findAll();
        assertThat(cryptoCurrencyTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CryptoCurrencyTransaction.class);
        CryptoCurrencyTransaction cryptoCurrencyTransaction1 = new CryptoCurrencyTransaction();
        cryptoCurrencyTransaction1.setId(1L);
        CryptoCurrencyTransaction cryptoCurrencyTransaction2 = new CryptoCurrencyTransaction();
        cryptoCurrencyTransaction2.setId(cryptoCurrencyTransaction1.getId());
        assertThat(cryptoCurrencyTransaction1).isEqualTo(cryptoCurrencyTransaction2);
        cryptoCurrencyTransaction2.setId(2L);
        assertThat(cryptoCurrencyTransaction1).isNotEqualTo(cryptoCurrencyTransaction2);
        cryptoCurrencyTransaction1.setId(null);
        assertThat(cryptoCurrencyTransaction1).isNotEqualTo(cryptoCurrencyTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CryptoCurrencyTransactionDTO.class);
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO1 = new CryptoCurrencyTransactionDTO();
        cryptoCurrencyTransactionDTO1.setId(1L);
        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO2 = new CryptoCurrencyTransactionDTO();
        assertThat(cryptoCurrencyTransactionDTO1).isNotEqualTo(cryptoCurrencyTransactionDTO2);
        cryptoCurrencyTransactionDTO2.setId(cryptoCurrencyTransactionDTO1.getId());
        assertThat(cryptoCurrencyTransactionDTO1).isEqualTo(cryptoCurrencyTransactionDTO2);
        cryptoCurrencyTransactionDTO2.setId(2L);
        assertThat(cryptoCurrencyTransactionDTO1).isNotEqualTo(cryptoCurrencyTransactionDTO2);
        cryptoCurrencyTransactionDTO1.setId(null);
        assertThat(cryptoCurrencyTransactionDTO1).isNotEqualTo(cryptoCurrencyTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cryptoCurrencyTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cryptoCurrencyTransactionMapper.fromId(null)).isNull();
    }*/
}
