package lotto.domain;

import lotto.constant.OutputMessage;
import lotto.constant.Ranking;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class LottoResult {

    private static final int DEFAULT = 0;
    private static final int INCREASE_SIZE = 1;
    private final Map<Ranking, Integer> elements;

    public LottoResult() {
        elements = new EnumMap<>(Ranking.class);
        Arrays.stream(Ranking.values()).forEach(value -> elements.put(value, DEFAULT));
    }

    public void increaseCount(Ranking ranking) {
        elements.put(ranking, elements.get(ranking) + INCREASE_SIZE);
    }

    public String getResultScreen() {
        StringBuilder sb = new StringBuilder();
        elements.keySet().stream()
                .filter(key -> key != Ranking.NONE)
                .forEach(key -> {
                    appendMatchCount(key, sb);
                    appendBonus(key, sb);
                    appendPrize(key, sb);
                    appendWinningNumber(key, sb);
                    sb.append(OutputMessage.NEW_LINE.getMessage());
                });
        return sb.toString();
    }

    private void appendMatchCount(Ranking ranking, StringBuilder sb) {
        OutputMessage message = OutputMessage.MATCH_COUNT;
        int matchNumber = ranking.getMatchCount();
        sb.append(String.format(message.getMessage(), matchNumber));
    }

    private void appendPrize(Ranking ranking, StringBuilder sb) {
        OutputMessage message = OutputMessage.PRIZE;
        int prize = ranking.getPrize();
        sb.append(String.format(message.getMessage(), prize));
    }

    private void appendBonus(Ranking ranking, StringBuilder sb) {
        if (ranking.isSecond()) {
            sb.append(OutputMessage.MATCH_BONUS.getMessage());
        }
    }

    private void appendWinningNumber(Ranking ranking, StringBuilder sb) {
        OutputMessage message = OutputMessage.RANKING_COUNT;
        int winningNumber = elements.get(ranking);
        sb.append(String.format(message.getMessage(), winningNumber));
    }
}