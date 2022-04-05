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

import java.util.Comparator;
import java.util.List;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.Test;
import org.mybatis.jpetstore.domain.Category;

//@Transactional
public class CategoryMapperTest {

	//@Inject
	private CategoryMapper mapper;
	
	public CategoryMapperTest() {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();

		mapper = container.select(CategoryMapper.class).get();
	}

	@Test
	void getCategoryList() {
		// given

		// when
		List<Category> categories = mapper.getCategoryList();

		// then
		categories.sort(Comparator.comparing(Category::getCategoryId));
		assertThat(categories).hasSize(5);
		assertThat(categories.get(0).getCategoryId()).isEqualTo("BIRDS");
		assertThat(categories.get(0).getName()).isEqualTo("Birds");
		assertThat(categories.get(0).getDescription())
				.isEqualTo("<image src=\"../images/birds_icon.gif\"><font size=\"5\" color=\"blue\"> Birds</font>");
		assertThat(categories.get(1).getCategoryId()).isEqualTo("CATS");
		assertThat(categories.get(1).getName()).isEqualTo("Cats");
		assertThat(categories.get(1).getDescription())
				.isEqualTo("<image src=\"../images/cats_icon.gif\"><font size=\"5\" color=\"blue\"> Cats</font>");
		assertThat(categories.get(2).getCategoryId()).isEqualTo("DOGS");
		assertThat(categories.get(2).getName()).isEqualTo("Dogs");
		assertThat(categories.get(2).getDescription())
				.isEqualTo("<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>");
		assertThat(categories.get(3).getCategoryId()).isEqualTo("dynamicQueryFISH");
		assertThat(categories.get(3).getName()).isEqualTo("Fish");
		assertThat(categories.get(3).getDescription())
				.isEqualTo("<image src=\"../images/fish_icon.gif\"><font size=\"5\" color=\"blue\"> Fish</font>");
		assertThat(categories.get(4).getCategoryId()).isEqualTo("REPTILES");
		assertThat(categories.get(4).getName()).isEqualTo("Reptiles");
		assertThat(categories.get(4).getDescription()).isEqualTo(
				"<image src=\"../images/reptiles_icon.gif\"><font size=\"5\" color=\"blue\"> Reptiles</font>");
	}

	@Test
	void getCategory() {
		// given
		String categoryId = "BIRDS";

		// when
		Category category = mapper.getCategory(categoryId);

		// then
		assertThat(category.getCategoryId()).isEqualTo("BIRDS");
		assertThat(category.getName()).isEqualTo("Birds");
		assertThat(category.getDescription())
				.isEqualTo("<image src=\"../images/birds_icon.gif\"><font size=\"5\" color=\"blue\"> Birds</font>");
	}

}
