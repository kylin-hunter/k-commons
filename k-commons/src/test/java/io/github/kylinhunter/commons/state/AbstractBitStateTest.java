package io.github.kylinhunter.commons.state;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AbstractBitStateTest {

  public enum TestState {
    STATE1,
    STATE2,
    STATE3
  }

  public static class TestStateBitState extends AbstractBitState<TestState> {}

  @Test
  void test() {

    TestStateBitState abstractBitState = new TestStateBitState();
    abstractBitState.setState(TestState.STATE1);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState.STATE1));
    assertFalse(abstractBitState.hasState(TestState.STATE2));
    assertFalse(abstractBitState.hasState(TestState.STATE3));

    abstractBitState.setState(TestState.STATE2);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState.STATE1));
    assertTrue(abstractBitState.hasState(TestState.STATE2));
    assertFalse(abstractBitState.hasState(TestState.STATE3));

    abstractBitState.setState(TestState.STATE3);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState.STATE1));
    assertTrue(abstractBitState.hasState(TestState.STATE2));
    assertTrue(abstractBitState.hasState(TestState.STATE3));

    abstractBitState.removeState(TestState.STATE3, TestState.STATE1);
    System.out.println(abstractBitState);

    assertFalse(abstractBitState.hasState(TestState.STATE1));
    assertTrue(abstractBitState.hasState(TestState.STATE2));
    assertFalse(abstractBitState.hasState(TestState.STATE3));
  }

  public enum TestState2 {
    STATE1,
    STATE2,
    STATE3,
    STATE4,
    STATE5,
    STATE6,
    STATE7,
    STATE8,
    STATE9,
    STATE10,
    STATE11,
    STATE12,
    STATE13,
    STATE14,
    STATE15,
    STATE16,
    STATE17,
    STATE18,
    STATE19,
    STATE20,
    STATE21,
    STATE22,
    STATE23,
    STATE24,
    STATE25,
    STATE26,
    STATE27,
    STATE28,
    STATE29,
    STATE30,
    STATE31,
    STATE32,
    STATE33,
    STATE34,
    STATE35,
    STATE36,
    STATE37,
    STATE38,
    STATE39,
    STATE40,
    STATE41,
    STATE42,
    STATE43,
    STATE44,
    STATE45,
    STATE46,
    STATE47,
    STATE48,
    STATE49,
    STATE50,
    STATE51,
    STATE52,
    STATE53,
    STATE54,
    STATE55,
    STATE56,
    STATE57,
    STATE58,
    STATE59,
    STATE60,
    STATE61,
    STATE62,
    STATE63,
    STATE64,
    STATE65
  }

  public static class TestStateBitState2 extends AbstractBitState<TestState2> {}

  @Test
  void test2() {

    TestStateBitState2 abstractBitState = new TestStateBitState2();
    abstractBitState.setState(TestState2.STATE1);
    abstractBitState.setState(TestState2.STATE63);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState2.STATE1));
    assertFalse(abstractBitState.hasState(TestState2.STATE2));
    assertFalse(abstractBitState.hasState(TestState2.STATE3));

    assertTrue(abstractBitState.hasState(TestState2.STATE63));
    assertFalse(abstractBitState.hasState(TestState2.STATE64));
    assertFalse(abstractBitState.hasState(TestState2.STATE65));

    abstractBitState.setState(TestState2.STATE2);
    abstractBitState.setState(TestState2.STATE64);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState2.STATE1));
    assertTrue(abstractBitState.hasState(TestState2.STATE2));
    assertFalse(abstractBitState.hasState(TestState2.STATE3));
    assertTrue(abstractBitState.hasState(TestState2.STATE63));
    assertTrue(abstractBitState.hasState(TestState2.STATE64));
    assertFalse(abstractBitState.hasState(TestState2.STATE65));

    abstractBitState.setState(TestState2.STATE3);
    abstractBitState.setState(TestState2.STATE65);
    System.out.println(abstractBitState);
    assertTrue(abstractBitState.hasState(TestState2.STATE1));
    assertTrue(abstractBitState.hasState(TestState2.STATE2));
    assertTrue(abstractBitState.hasState(TestState2.STATE3));
    assertTrue(abstractBitState.hasState(TestState2.STATE63));
    assertTrue(abstractBitState.hasState(TestState2.STATE64));
    assertTrue(abstractBitState.hasState(TestState2.STATE65));

    abstractBitState.removeState(
        TestState2.STATE1, TestState2.STATE3, TestState2.STATE63, TestState2.STATE65);
    System.out.println(abstractBitState);

    assertFalse(abstractBitState.hasState(TestState2.STATE1));
    assertTrue(abstractBitState.hasState(TestState2.STATE2));
    assertFalse(abstractBitState.hasState(TestState2.STATE3));

    assertFalse(abstractBitState.hasState(TestState2.STATE63));
    assertTrue(abstractBitState.hasState(TestState2.STATE64));
    assertFalse(abstractBitState.hasState(TestState2.STATE65));
  }
}
