import java.util.Collections;
import java.util.Map;

public class TennisGame1 implements TennisGame {

    private static final String LOVE = "Love";
    private static final String FIFTEEN = "Fifteen";
    private static final String THIRTY = "Thirty";
    private static final String FORTY = "Forty";
    private static final String ALL = "All";
    private static final String DEUCE = "Deuce";
    private static final String ADVANTAGE = "Advantage ";
    private static final String WIN_FOR = "Win for ";
    private static final String HYPHEN = "-";

    private static Map<Integer, String> tieMap = Collections.unmodifiableMap(
            Map.of(
                    0, LOVE + HYPHEN + ALL,
                    1, FIFTEEN + HYPHEN + ALL,
                    2, THIRTY + HYPHEN + ALL
            ));

    private static Map<Integer, String> pointMap = Collections.unmodifiableMap(
            Map.of(
                    0, LOVE,
                    1, FIFTEEN,
                    2, THIRTY
            ));

    private final String player1Name;
    private final String player2Name;

    private int player1Score;
    private int player2Score;
    private int absoluteScoreDifference;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        updatePlayerScore(playerName);
        computeAbsoluteScoreDifference();
    }

    private void updatePlayerScore(String playerName) {
        if (player1Name.equals(playerName))
            player1Score += 1;
        else
            player2Score += 1;
    }

    private void computeAbsoluteScoreDifference() {
        absoluteScoreDifference = Math.abs(player1Score - player2Score);
    }

    public String getScore() {
        if (absoluteScoreDifference == 0) {
            return computeTieScore();
        } else if ((player1Score | player2Score) >= 4 && absoluteScoreDifference == 1) {
            return computeAdvantageScore();
        } else if ((player1Score | player2Score) >= 4 && absoluteScoreDifference > 1) {
            return computeWinScore();
        }
        return computeScore();
    }

    private String computeTieScore() {
        return tieMap.getOrDefault(player1Score, DEUCE);
    }

    private String computeAdvantageScore() {
        if (player1Score > player2Score) {
            return ADVANTAGE + player1Name;
        }
        return ADVANTAGE + player2Name;
    }

    private String computeWinScore() {
        if (player1Score > player2Score) {
            return WIN_FOR + player1Name;
        }
        return WIN_FOR + player2Name;
    }

    private String computeScore() {
        return getLiteralPlayerScoreFrom(player1Score) + HYPHEN + getLiteralPlayerScoreFrom(player2Score);
    }

    private String getLiteralPlayerScoreFrom(int numericPlayerScore) {
        return pointMap.getOrDefault(numericPlayerScore, FORTY);
    }
}
