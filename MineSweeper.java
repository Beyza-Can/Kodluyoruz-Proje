import java.util.Scanner;

public class MineSweeper {
    private int rowSize;
    private int colSize;
    private int mineCount;
    private int[][] board;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean gameOver;
    private boolean gameWon;

    public MineSweeper(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.mineCount = rowSize * colSize / 4;
        this.board = new int[rowSize][colSize];
        this.mines = new boolean[rowSize][colSize];
        this.revealed = new boolean[rowSize][colSize];
        this.gameOver = false;
        this.gameWon = false;

        placeMines();

        calculateNumbers();
    }

    private void placeMines() {
        int minesToPlace = mineCount;
        while (minesToPlace > 0) {
            int row = (int) (Math.random() * rowSize);
            int col = (int) (Math.random() * colSize);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesToPlace--;
            }
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (mines[i][j]) {
                    incrementSurrounding(i - 1, j - 1);
                    incrementSurrounding(i - 1, j);
                    incrementSurrounding(i - 1, j + 1);
                    incrementSurrounding(i, j - 1);
                    incrementSurrounding(i, j + 1);
                    incrementSurrounding(i + 1, j - 1);
                    incrementSurrounding(i + 1, j);
                    incrementSurrounding(i + 1, j + 1);
                }
            }
        }
    }

    private void incrementSurrounding(int row, int col) {
        if (row >= 0 && row < rowSize && col >= 0 && col < colSize && !mines[row][col]) {
            board[row][col]++;
        }
    }

    public void printBoard() {
        System.out.println("Mayınların Konumu");
        printMatrix(mines);
        System.out.println("===========================");

        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        printMatrix(revealed);
        System.out.println("===========================");
    }

    private void printMatrix(boolean[][] matrix) {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (matrix[i][j]) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (!gameOver && !gameWon) {
            printBoard();
            System.out.print("Satır Giriniz : ");
            int row = scanner.nextInt();
            System.out.print("Sütun Giriniz : ");
            int col = scanner.nextInt();

            if (isValidMove(row, col)) {
                if (mines[row][col]) {
                    System.out.println("Oyunu Kaybettiniz!!");
                    gameOver = true;
                } else {
                    revealCell(row, col);
                    if (checkWin()) {
                        System.out.println("Oyunu Kazandınız !");
                        gameWon = true;
                    }
                }
            } else {
                System.out.println("Geçersiz hamle, lütfen tekrar girin.");
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize && !revealed[row][col];
    }

    private void revealCell(int row, int col) {
        if (isValidMove(row, col)) {
            revealed[row][col] = true;
            if (board[row][col] == 0) {
                revealCell(row - 1, col - 1);
                revealCell(row - 1, col);
                revealCell(row - 1, col + 1);
                revealCell(row, col - 1);
                revealCell(row, col + 1);
                revealCell(row + 1, col - 1);
                revealCell(row + 1, col);
                revealCell(row + 1, col + 1);
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (!mines[i][j] && !revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Satır sayısını girin: ");
        int rowSize = scanner.nextInt();
        System.out.print("Sütun sayısını girin: ");
        int colSize = scanner.nextInt();

        MineSweeper game = new MineSweeper(rowSize, colSize);
        game.playGame();
    }
}
