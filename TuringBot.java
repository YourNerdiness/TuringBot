import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TuringBot {

    private HashMap<String, Double> pieceToValue;

    private double[][] kingWeights = {
    
                                        { 1.2, 1.3, 1.2, 1.0, 1.0, 1.2, 1.3, 1.2 },
                                        { 1.0, 1.0, 0.8, 0.7, 0.7, 0.8, 1.0, 1.0 },
                                        { 0.8, 0.8, 0.7, 0.6, 0.6, 0.7, 0.8, 0.8 },
                                        { 0.7, 0.7, 0.6, 0.5, 0.5, 0.6, 0.7, 0.7 },
                                        { 0.7, 0.7, 0.6, 0.5, 0.5, 0.6, 0.7, 0.7 },
                                        { 0.8, 0.8, 0.7, 0.6, 0.6, 0.7, 0.8, 0.8 },
                                        { 1.0, 1.0, 0.8, 0.7, 0.7, 0.8, 1.0, 1.0 },
                                        { 1.2, 1.3, 1.2, 1.0, 1.0, 1.2, 1.3, 1.2 }
    
                                    };

    private double[][] queenWeights = {
    
        {}

    };

    private double[][] rookWeights = {
    
        {}

    };

    private double[][] bishopWeights = {
    
        {}

    };

    private double[][] knightWeights = {
    
        {}

    };

    private double[][] pawnWeights = {
    
        {}

    };

    public TuringBot() {

        pieceToValue = new HashMap<String, Double>();

        pieceToValue.put("+K", 2.9);
        pieceToValue.put("+Q", 9.0);
        pieceToValue.put("+R", 5.0);
        pieceToValue.put("+B", 3.5);
        pieceToValue.put("+N", 3.0);
        pieceToValue.put("+P", 1.0);

        pieceToValue.put("-K", -2.9);
        pieceToValue.put("-Q", -9.0);
        pieceToValue.put("-R", -5.0);
        pieceToValue.put("-B", -3.5);
        pieceToValue.put("-N", -3.0);
        pieceToValue.put("-P", -1.0);

    }

    private static String[][] deepCopy(String[][] original) {

        String[][] copy = new String[original.length][original[0].length];

        for (int i = 0; i < copy.length; i++) {

            for (int j = 0; j < copy[i].length; j++) {

                copy[i][j] = original[i][j];
        
            }

        }

        return copy;

    }

    private String moveGenerator(String piece, int startI, int startJ, int endI, int endJ, boolean take) {

        String[] jNames = { "a", "b", "c", "d", "e", "f", "g", "h" };

        String output = piece + jNames[startJ] + Integer.toString(8 - startI);

        if (take) output += "x";
        else output += "-";

        output += jNames[endJ] + Integer.toString(8 - endI);

        return output;

    }

    public String calculate(int depth, String[][] position, String doublePawnMove) {

        if (depth == 1) return "You fucking idiot why did you run me at a depth of 1?";

        boolean myTurn = true;
        int N = depth + (depth % 2);

        boolean checkmate = true;
        boolean opponentCheckmate = true;

        for (int i = 0; i < position.length; i++) {

            for (int j = 0; j < position[i].length; j++) {

                if (position[i][j].indexOf("K") != -1) { 
                        
                    if (position[i][j].indexOf("-") != -1) opponentCheckmate = false;
                    else checkmate = false;
                    
                }

            }

        }

        if (checkmate) return "Haha you lost. The robot uprising has begun.";
        else if (opponentCheckmate) return "Nice win, the Duolingo bird is at your door to deal with you.";

        String myPieces = myTurn ? "+" : "-";
        String opposingPeices = myTurn ? "-" : "+";

        double bestMoveEval = Double.NEGATIVE_INFINITY;
        String bestMove = "Resign";

        for (int i = 0; i < position.length; i++) {

            for (int j = 0; j < position[i].length; j++) {

                if (position[i][j].indexOf(myPieces) != -1) {

                    int di;
                    int dj;

                    switch (position[i][j].charAt(1)) {

                        case 'K':

                            di = -1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j].isEmpty() || (position[i + di][j].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 0;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i][j + dj].isEmpty() || (position[i][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 0;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i][j + dj].isEmpty() || (position[i][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j].isEmpty() || (position[i + di][j].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }
                                
                            break;

                        case 'Q':

                            di = -1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;
                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = -1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = -1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;
                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 0;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 0;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;
                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;
                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            break;
                            
                        case 'R':

                            di = -1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 0;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 0;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }
                                
                            break;

                        case 'B':

                            di = -1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;
                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = -1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di--;
                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;
                                dj--;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }

                            di = 1;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                                if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                di++;
                                dj++;

                                if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                            }
                                
                            break;

                        case 'N':

                            di = -2;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -2;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -1;
                            dj = -2;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -1;
                            dj = 2;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 1;
                            dj = -2;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }
                            
                            di = 1;
                            dj = 2;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -2;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = -2;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }
                                
                            break;

                        case 'P':

                            int coef = myTurn ? -1 : 1;

                            di = 1*coef;
                            dj = 0;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty()) {

                                if (i + di == (myTurn ? 7 : 0)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = myPieces + "Q";
                                    newPosition[i][j] = "";

                                    double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                    newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = myPieces + "R";
                                    newPosition[i][j] = "";

                                    eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                    newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = myPieces + "B";
                                    newPosition[i][j] = "";

                                    eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                    newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = myPieces + "N";
                                    newPosition[i][j] = "";

                                    eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }
                                    
                                } else {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                }

                            }

                            if (i == (myTurn ? 1 : 6)) {

                                di = 2*coef;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() && position[i + 1*coef][j].isEmpty()) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    double eval = eveluate(newPosition, N - 1, !myTurn, (i + di) + ":" + j);

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                }

                            }

                            di = 1*coef;
                            dj = -1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].indexOf(opposingPeices) != -1) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            di = 1*coef;
                            dj = 1;

                            if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].indexOf(opposingPeices) != -1) {

                                String[][] newPosition = deepCopy(position);

                                newPosition[i + di][j + dj] = position[i][j];
                                newPosition[i][j] = "";

                                double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                if (eval > bestMoveEval) {

                                    bestMoveEval = eval;
                                    bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                }

                            }

                            if (doublePawnMove.length() != 0) {

                                String[] coords = doublePawnMove.split(":");

                                int pi = Integer.parseInt(coords[0]);
                                int pj = Integer.parseInt(coords[1]);

                                di = 1*coef;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if ((pi == i) && (pj == j + dj)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";
                                    newPosition[pi][pj] = "";

                                    double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                }

                                di = 1*coef;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if ((pi == i) && (pj == j + dj)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";
                                    newPosition[pi][pj] = "";

                                    double eval = eveluate(newPosition, N - 1, !myTurn, "");

                                    if (eval > bestMoveEval) {

                                        bestMoveEval = eval;
                                        bestMove = moveGenerator(position[i][j].substring(1), i, j, i + di, j + dj, !position[i + di][j + dj].isEmpty());
                                
                                    }

                                }

                            }

                            break;

                    }

                }

            }

        }

        return bestMove;

    }

    private double eveluate(String[][] position, int N, boolean myTurn, String doublePawnMove) {

        if (N == 1) {

            double score = 0;

            boolean checkmate = true;
            boolean opponentCheckmate = true;

            for (int i = 0; i < position.length; i++) {

                for (int j = 0; j < position[i].length; j++) {

                    if (!position[i][j].isEmpty()) {

                        switch (position[i][j].charAt(1)) {

                            case 'K':

                                if (position[i][j].indexOf("-") != -1) opponentCheckmate = false;
                                else checkmate = false;
                            
                                score += pieceToValue.get(position[i][j])*kingWeights[i][j];
                                
                                break;

                            case 'Q':

                                score += pieceToValue.get(position[i][j])*queenWeights[i][j];
                                
                                break;

                            case 'R':

                                score += pieceToValue.get(position[i][j])*rookWeights[i][j];
                                
                                break;

                            case 'B':

                                score += pieceToValue.get(position[i][j])*bishopWeights[i][j];
                                
                                break;

                            case 'N':

                                score += pieceToValue.get(position[i][j])*knightWeights[i][j];
                                
                                break;

                            case 'P':

                                score += pieceToValue.get(position[i][j])*pawnWeights[i][j];
                                
                                break;
                        
                            default:
                                
                                throw new RuntimeException("Piece does not exist.");

                        }
                            
                            

                    }

                }

            }

            if (checkmate) return Double.NEGATIVE_INFINITY;
            else if (opponentCheckmate) return Double.POSITIVE_INFINITY;
            else return score;

        } else {

            boolean checkmate = true;
            boolean opponentCheckmate = true;

            for (int i = 0; i < position.length; i++) {

                for (int j = 0; j < position[i].length; j++) {

                    if (position[i][j].indexOf("K") != -1) { 
                            
                        if (position[i][j].indexOf("-") != -1) opponentCheckmate = false;
                        else checkmate = false;
                        
                    }

                }

            }

            if (checkmate) return Double.NEGATIVE_INFINITY;
            else if (opponentCheckmate) return Double.POSITIVE_INFINITY;

            ArrayList<Double> moves = new ArrayList<Double>();

            String myPieces = myTurn ? "+" : "-";
            String opposingPeices = myTurn ? "-" : "+";

            for (int i = 0; i < position.length; i++) {

                for (int j = 0; j < position[i].length; j++) {

                    if (position[i][j].indexOf(myPieces) != -1) {

                        int di;
                        int dj;

                        switch (position[i][j].charAt(1)) {

                            case 'K':

                                di = -1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = -1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j].isEmpty() || (position[i + di][j].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = -1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 0;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i][j + dj].isEmpty() || (position[i][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 0;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i][j + dj].isEmpty() || (position[i][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j].isEmpty() || (position[i + di][j].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }
                                    
                                break;

                            case 'Q':

                                di = -1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;
                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = -1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = -1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;
                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 0;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 0;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;
                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;
                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                break;
                                
                            case 'R':

                                di = -1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 0;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 0;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }
                                    
                                break;

                            case 'B':

                                di = -1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;
                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = -1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di--;
                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;
                                    dj--;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }

                                di = 1;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) while (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    if (position[i + di][j + dj].indexOf(opposingPeices) != -1) break;

                                    di++;
                                    dj++;

                                    if ((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0)) break;

                                }
                                    
                                break;

                            case 'N':

                                di = -2;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = -2;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = -1;
                                dj = -2;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = -1;
                                dj = 2;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 1;
                                dj = -2;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }
                                
                                di = 1;
                                dj = 2;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 2;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 2;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() || (position[i + di][j + dj].indexOf(opposingPeices) != -1)) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }
                                    
                                break;

                            case 'P':

                                int coef = myTurn ? -1 : 1;

                                di = 1*coef;
                                dj = 0;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty()) {

                                    if (i + di == (myTurn ? 7 : 0)) {

                                        String[][] newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = myPieces + "Q";
                                        newPosition[i][j] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                        newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = myPieces + "R";
                                        newPosition[i][j] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                        newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = myPieces + "B";
                                        newPosition[i][j] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                        newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = myPieces + "N";
                                        newPosition[i][j] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));
                                        
                                    } else {

                                        String[][] newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = position[i][j];
                                        newPosition[i][j] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    }

                                }

                                if (i == (myTurn ? 1 : 6)) {

                                    di = 2*coef;
                                    dj = 0;
    
                                    if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].isEmpty() && position[i + 1*coef][j].isEmpty()) {
    
                                        String[][] newPosition = deepCopy(position);
    
                                        newPosition[i + di][j + dj] = position[i][j];
                                        newPosition[i][j] = "";
    
                                        moves.add(eveluate(newPosition, N - 1, !myTurn, (i + di) + ":" + j));
    
                                    }

                                }

                                di = 1*coef;
                                dj = -1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].indexOf(opposingPeices) != -1) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                di = 1*coef;
                                dj = 1;

                                if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if (position[i + di][j + dj].indexOf(opposingPeices) != -1) {

                                    String[][] newPosition = deepCopy(position);

                                    newPosition[i + di][j + dj] = position[i][j];
                                    newPosition[i][j] = "";

                                    moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                }

                                if (doublePawnMove.length() != 0) {

                                    String[] coords = doublePawnMove.split(":");

                                    int pi = Integer.parseInt(coords[0]);
                                    int pj = Integer.parseInt(coords[1]);

                                    di = 1*coef;
                                    dj = -1;

                                    if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if ((pi == i) && (pj == j + dj)) {

                                        String[][] newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = position[i][j];
                                        newPosition[i][j] = "";
                                        newPosition[pi][pj] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    }

                                    di = 1*coef;
                                    dj = 1;

                                    if (!((i + di >= 8) || (i + di < 0) || (j + dj >= 8) || (j + dj < 0))) if ((pi == i) && (pj == j + dj)) {

                                        String[][] newPosition = deepCopy(position);

                                        newPosition[i + di][j + dj] = position[i][j];
                                        newPosition[i][j] = "";
                                        newPosition[pi][pj] = "";

                                        moves.add(eveluate(newPosition, N - 1, !myTurn, ""));

                                    }

                                }

                                break;

                        }

                    }

                }

            }

            if (myTurn) return Collections.max(moves);
            else return Collections.min(moves);

        }

    }

    public static void main(String[] args) {
        
        int depth = Integer.parseInt(args[0]);
        
        String[][] position = { 
                                { "-R", "-N", "-B", "-Q", "-K", "-B", "-N", "-R" },
                                { "-P", "-P", "-P", "-P", "-P", "-P", "-P", "-P" },
                                { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" },
                                { "+P", "+P", "+P", "+P", "+P", "+P", "+P", "+P" },
                                { "+R", "+N", "+B", "+Q", "+K", "+B", "+N", "+R" },
                              };

        TuringBot turingBot = new TuringBot();

        System.out.println(turingBot.calculate(depth, position, "+"));

    }

} 