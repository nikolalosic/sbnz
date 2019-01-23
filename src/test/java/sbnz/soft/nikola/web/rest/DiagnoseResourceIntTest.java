package sbnz.soft.nikola.web.rest;

import sbnz.soft.nikola.SbnzApp;

import sbnz.soft.nikola.domain.Diagnose;
import sbnz.soft.nikola.repository.DiagnoseRepository;
import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;
import sbnz.soft.nikola.service.mapper.DiagnoseMapper;
import sbnz.soft.nikola.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static sbnz.soft.nikola.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiagnoseResource REST controller.
 *
 * @see DiagnoseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class DiagnoseResourceIntTest {


}
