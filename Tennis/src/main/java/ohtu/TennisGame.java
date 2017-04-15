package ohtu;

public class TennisGame {

    private int p1Score = 0;
    private int p2Score = 0;
    private String p1Name;
    private String p2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.p1Name = player1Name;
        this.p2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if ("player1".equals(playerName)) {
            p1Score += 1;
        } else {
            p2Score += 1;
        }
    }

    public String getScore() {
        String score = "";
        if (p1Score == p2Score) {
            score = scoreInDraw();
        } else if (p1Score >= 4 || p2Score >= 4) {
            score = scoreInAdvantageOrWin();
        } else {
            score = pointsAsString(p1Score) + "-" + pointsAsString(p2Score);
        }
        return score;
    }

    private String scoreInDraw() {
        String score;
        if (p1Score == 4) {
            score = "Deuce";
        } else {
            score = pointsAsString(p1Score) + "-All";
        }
        return score;
    }

    private String scoreInAdvantageOrWin() {
        String score;
        int minusResult = p1Score - p2Score;
        if (minusResult == 1) {
            score = "Advantage player1";
        } else if (minusResult == -1) {
            score = "Advantage player2";
        } else if (minusResult >= 2) {
            score = "Win for player1";
        } else {
            score = "Win for player2";
        }
        return score;
    }

    private String pointsAsString(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            default:
                return "Forty";
        }
    }
}
