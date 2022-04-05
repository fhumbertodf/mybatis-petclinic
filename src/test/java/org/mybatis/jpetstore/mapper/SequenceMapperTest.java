/*
 *    Copyright 2010-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.ibatis.session.SqlSession;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.Test;
import org.mybatis.jpetstore.domain.Sequence;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = MapperTestContext.class)
//@Transactional
class SequenceMapperTest {

	// @Autowired
	private SequenceMapper mapper;

	// @Autowired
	// private JdbcTemplate jdbcTemplate;

	private SqlSession sqlSession;

	public SequenceMapperTest() {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();

		mapper = container.select(SequenceMapper.class).get();
		sqlSession = container.select(SqlSession.class).get();
	}

	@Test
	void getSequence() {
		// given

		// when
		Sequence sequence = mapper.getSequence(new Sequence("ordernum", -1));

		// then
		assertThat(sequence.getName()).isEqualTo("ordernum");
		assertThat(sequence.getNextId()).isEqualTo(1000);
	}

	@Test
	void updateSequence() {
		// given
		Sequence sequence = new Sequence("ordernum", 1001);

		// when
		mapper.updateSequence(sequence);

		// then
		Integer id = sqlSession.selectOne("org.mybatis.jpetstore.mapper.SequenceMapper.dynamicQuery",
				"SELECT nextid FROM sequence WHERE name = 'ordernum'");
		assertThat(id).isEqualTo(1001);
	}

}
