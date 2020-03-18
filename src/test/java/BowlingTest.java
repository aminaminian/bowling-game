import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BowlingTest {

    @Test
    public void score_0_when_failing_all_the_rolls() {
        assertThat(scoreOf("--|--|--|--|--|--|--|--|--|--||"), is(0));
    }

    @Test
    public void score_1_when_the_first_roll_throw_one_pin_and_miss_others_rolls() {
        assertThat(scoreOf("1-|--|--|--|--|--|--|--|--|--||"), is(1));
    }

    @Test
    public void score_2_when_the_first_roll_throw_two_pin_and_miss_others_rolls() {
        assertThat(scoreOf("2-|--|--|--|--|--|--|--|--|--||"), is(2));
    }

    @Test
    public void score_20_when_all_rolls_throw_one_pin() {
        assertThat(scoreOf("11|11|11|11|11|11|11|11|11|11||"), is(20));
    }

    @Test
    public void score_10_when_the_first_turn_do_a_spare_and_miss_other_rolls() {
        assertThat(scoreOf("3/|--|--|--|--|--|--|--|--|--||"), is(10));
    }

    @Test
    public void score_10_when_the_first_turn_do_a_strike_and_miss_other_rolls() {
        assertThat(scoreOf("X|--|--|--|--|--|--|--|--|--||"), is(10));
    }

    @Test
    public void get_next_throw_of_bonus_after_spare() {
        assertThat(scoreOf("3/|3-|--|--|--|--|--|--|--|--||"), is(16));
    }

    @Test
    public void not_be_a_spare_the_score_of_10_in_two_consecutive_rolls_but_in_different_turns() {
        assertThat(scoreOf("35|53|--|--|--|--|--|--|--|--||"), is(16));
    }

    @Test
    public void be_next_two_rolls_of_bonus_after_strike() {
        assertThat(scoreOf("X|53|--|--|--|--|--|--|--|--||"), is(26));
    }

    @Test
    public void scores_300_a_perfect_game() {
        assertThat(scoreOf("X|X|X|X|X|X|X|X|X|X||XX"), is(300));
    }

    @Test
    public void scores_150_all_the_turns_with_spare_and_the_last_bonus_5() {
        assertThat(scoreOf("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5"), is(150));
    }

    @Test
    public void scores_90_all_the_turns_with_spare_and_no_bonus() {
        assertThat(scoreOf("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||"), is(90));
    }

    @Test
    public void scores_167_all_the_turns_with_spare_and_strike_and_the_last_bonus_9() {
        assertThat(scoreOf("X|7/|9-|X|-8|8/|-6|X|X|X||81"), is(167));
    }

    @Test
    public void scores_146_all_the_turns_with_spare_and_strike_and_the_last_bonus_10() {
        assertThat(scoreOf("32|-/|X|1/|34|-1|3/|X|X|-/||X"), is(146));
    }

    @Test
    public void scores_191_all_the_turns_with_spare_and_strike_and_no_bonus() {
        assertThat(scoreOf("X|X|X|X|-/|14|X|X|X|16||"), is(191));
    }

    @Test
    public void scores_219_all_the_turns_with_spare_and_strike_and_no_bonus() {
        assertThat(scoreOf("18|X|X|X|X|X|X|X|X|--||"), is(219));
    }

    private int scoreOf(String gameResult) {
        CalculateScores calculateScores = new CalculateScores();
        return calculateScores.getFinalScore(gameResult);
    }
}
