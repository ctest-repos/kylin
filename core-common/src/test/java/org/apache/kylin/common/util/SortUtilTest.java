/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.common.util;

import org.junit.runner.RunWith;
import edu.illinois.CTestJUnit4Runner2;
import edu.illinois.CTestClass;
import edu.illinois.CTest;

import org.junit.Test;

import java.time.Month;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Unit tests for class {@link SortUtil}.
 *
 * @see SortUtil
 *
 */
@RunWith(CTestJUnit4Runner2.class)
@CTestClass()
public class SortUtilTest{

  @Test
  public void testExtractAndSort() {
      Stack<Month> stack = new Stack<>();
      ListIterator<Month> listIterator = stack.listIterator();
      Iterator<Month> iterator = SortUtil.extractAndSort(listIterator, null);

      assertTrue(stack.empty());
      assertEquals("[]", stack.toString());

      assertEquals(10, stack.capacity());
      assertEquals(0, stack.size());

      assertTrue(stack.isEmpty());
      assertFalse(listIterator.hasNext());

      assertFalse(listIterator.hasPrevious());
      assertNotNull(iterator);
  }

}