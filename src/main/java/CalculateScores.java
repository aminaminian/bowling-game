import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static util.Utils.*;

public class CalculateScores {
    public int getFinalScore(final String gameResult){
        return calculateElementsOneByOne(convertScoresStringToArray(gameResult));
    }

    private String[] convertScoresStringToArray(String gameResult){
        return Stream.of(gameResult.split(FRAME_BOUNDARY_PIPE))
                .map(element -> element.replace(MISSED_BALL, STRING_ZERO)) // replace "-" with "0"
                .map(element -> element.replace(STRIKE, String.valueOf(STRIKE_SCORE))) // replace "X" with "10"
                .toArray(String[]::new);
    }

    private int calculateElementsOneByOne(final String[] scores){
        AtomicInteger totalScoreScore = new AtomicInteger();
        AtomicInteger frameBoundaryOccurred = new AtomicInteger(0);
        IntStream.range(START_INCLUSIVE, scores.length).forEach(scoreIndex -> {
            if(isNotFrameBoundary(scores[scoreIndex]) && frameBoundaryOccurred.get() <= NINTH_FRAME) {
                if (isStrike(scores[scoreIndex]))
                    totalScoreScore.getAndAdd(calculateStrikePlusNextTwoBalls(scores, scoreIndex, frameBoundaryOccurred.intValue()));
                else if(isNotSpare(scores[scoreIndex]))
                    totalScoreScore.getAndAdd(inInteger(scores[scoreIndex]));
                else if(isSpare(scores[scoreIndex]))
                    totalScoreScore.getAndAdd(getSpareScore(scores, scoreIndex, frameBoundaryOccurred.intValue()));
            }else
                frameBoundaryOccurred.getAndIncrement();
        });
        return totalScoreScore.intValue();
    }

    private int calculateStrikePlusNextTwoBalls(String[] scores, int scoreIndex, int frameBoundaryOccurred){

        // totalScore is initialized to 10 because when method calls that means user got a Strike
        int totalScore = 10;
        int nextIndex = scoreIndex;
        int nextBall = 1;
        int nextNextBall = 1;
        // find out scores of next two balls)
        for(int i = scoreIndex; i < nextIndex + 2 && nextBall < NUMBER_OF_LOOP; scoreIndex += 2) {
            nextIndex += 2; // addition two to skip frame boundary("|")
            if (isStrike(scores[nextIndex]) && frameBoundaryOccurred < EIGHTH_FRAME) { // next two strike balls up to lesser than eight frame
                totalScore += STRIKE_SCORE;
                nextBall++;
            } else if (isStrike(scores[nextIndex]) && frameBoundaryOccurred == EIGHTH_FRAME) { // next two strike balls in eight frame
                totalScore += getScoreFrameEightPlusOneBonusBall(scores, nextIndex);
                break;
            } else if (isNotStrike(scores[nextIndex]) && frameBoundaryOccurred == EIGHTH_FRAME) { // next two non strike balls in eight frame
                if(isSpare(scores[nextIndex + nextNextBall])) {
                   totalScore += SPARE_SCORE;
                   break;
                }else {
                    totalScore += getScoreOfBothBalls(scores, nextIndex);
                    break;
                }
            } else if (frameBoundaryOccurred == NINTH_FRAME) {
                totalScore += getTotalScoreInNinthFrame(scores, nextIndex);
                break;
            } else if (isNotStrike(scores[nextIndex])) {
                if (nextBall == 2) {
                    totalScore += getScoreOfSecondBall(scores, nextIndex);
                    break;
                } else if (isSpare(scores[nextIndex + 1])) {
                    totalScore += SPARE_SCORE;
                    break;
                } else {
                    totalScore += getScoreOfBothBalls(scores, nextIndex);
                    break;
                }
            }
        }
        return totalScore;
    }

    private int getSpareScore(String[] scores, int scoreIndex, int frameBoundaryOccurred){
        if (frameBoundaryOccurred == 9)
            return getScoreOfBonusBallInLastFrame(scores, scoreIndex);

        return getScoreOfNextBall(scores, scoreIndex);
    }

    private int getScoreOfSecondBall(String[] scores, int nextIndex){
        return inInteger(scores[nextIndex]);
    }

    private int getScoreFrameEightPlusOneBonusBall(String[] scores, int nextIndex){
        return STRIKE_SCORE + inInteger(scores[nextIndex + INCREMENT_BY_THREE]);
    }

    private int getScoreOfBothBalls(String[] scores, int nextIndex){
        return inInteger(scores[nextIndex]) + inInteger(scores[nextIndex + INCREMENT_BY_ONE]);
    }

    private int getTotalScoreInNinthFrame(String[] scores, int nextIndex){
        return  inInteger(scores[nextIndex + INCREMENT_BY_ONE]) + inInteger(scores[nextIndex + INCREMENT_BY_TWO]);
    }

    private int getScoreOfBonusBallInLastFrame(String[] scores, int scoreIndex){
        return (SPARE_SCORE - inInteger(scores[scoreIndex - DECREASE_BY_ONE])) + inInteger(scores[scoreIndex + INCREMENT_BY_THREE]);
    }

    private int getScoreOfNextBall(String[] scores, int scoreIndex){
        return (SPARE_SCORE - inInteger(scores[scoreIndex - DECREASE_BY_ONE])) + inInteger(scores[scoreIndex + INCREMENT_BY_TWO]);
    }
}