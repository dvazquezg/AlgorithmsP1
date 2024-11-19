package module2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private double[][] grid;
    private int n;
    private int openSites;
    private WeightedQuickUnionUF wUnionFind;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.grid = new double[n][n];
        this.wUnionFind = new WeightedQuickUnionUF(n * n + 2); // include two virtual sites
        this.n = n;
        this.openSites = 0;

        // initialize all sites to be blocked
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.grid[row][col] = 0; // blocked site
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }

        if (this.grid[row - 1][col - 1] == 1) return;
        this.grid[row - 1][col - 1] = 1; // open site
        this.openSites++;
        int siteNum = coordinateToNum(row, col);

        // connect to adjacent open-top site (or virtual top site 0)
        if (row - 1 == 0) {
            this.wUnionFind.union(siteNum, 0);
        } else if (this.grid[row - 2][col - 1] == 1) {
            this.wUnionFind.union(siteNum, siteNum - this.n);
        }

        // connect to adjacent open-bottom site (or virtual bottom site n * n + 1)
        if (row + 1 > this.n) {
            this.wUnionFind.union(siteNum, this.n * this.n + 1);
        } else if (this.grid[row][col - 1] == 1) {
            this.wUnionFind.union(siteNum, siteNum + this.n);
        }

        // connect to adjacent open-left site
        if (col - 1 > 0 && this.grid[row - 1][col - 2] == 1) {
            this.wUnionFind.union(siteNum, siteNum - 1);
        }

        // connect to adjacent open-right site
        if (col + 1 <= this.n && this.grid[row - 1][col] == 1) {
            this.wUnionFind.union(siteNum, siteNum + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
        int siteNum = coordinateToNum(row, col);
        return this.wUnionFind.find(siteNum) == this.wUnionFind.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // check if virtual sites 0 and n * n + 1 are connected (have the same parent)
        return this.wUnionFind.find(0) == this.wUnionFind.find(this.n * this.n + 1);
    }

    private int coordinateToNum(int row, int col) {
        return (row - 1) * this.n + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);
        StdOut.println("Percolates? " + percolation.percolates());

        percolation.open(1, 6);
        StdOut.println("Is (1, 6) full? " + percolation.isFull(1, 6));
        percolation.open(2, 6);
        StdOut.println("Is (2, 6) full? " + percolation.isFull(2, 6));
        percolation.open(3, 6);
        StdOut.println("Is (3, 6) full? " + percolation.isFull(3, 6));
        percolation.open(4, 6);
        StdOut.println("Is (4, 6) full? " + percolation.isFull(4, 6));
        percolation.open(5, 6);
        StdOut.println("Is (5, 6) full? " + percolation.isFull(5, 6));
        percolation.open(5, 5);
        StdOut.println("Is (5, 5) full? " + percolation.isFull(5, 5));
        percolation.open(4, 4);
        StdOut.println("Is (4, 4) full? " + percolation.isFull(4, 4));
        percolation.open(3, 4);
        StdOut.println("Is (3, 4) full? " + percolation.isFull(3, 4));
        percolation.open(2, 4);
        StdOut.println("Is (2, 4) full? " + percolation.isFull(2, 4));
        percolation.open(2, 3);
        StdOut.println("Is (2, 3) full? " + percolation.isFull(2, 3));
        percolation.open(2, 2);
        StdOut.println("Is (2, 2) full? " + percolation.isFull(2, 2));
        percolation.open(2, 1);
        StdOut.println("Is (2, 1) full? " + percolation.isFull(2, 1));
        percolation.open(3, 1);
        StdOut.println("Is (3, 1) full? " + percolation.isFull(3, 1));
        percolation.open(4, 1);
        StdOut.println("Is (4, 1) full? " + percolation.isFull(4, 1));
        percolation.open(5, 1);
        StdOut.println("Is (5, 1) full? " + percolation.isFull(5, 1));
        percolation.open(5, 2);
        StdOut.println("Is (5, 2) full? " + percolation.isFull(5, 2));
        percolation.open(6, 2);
        StdOut.println("Is (6, 2) full? " + percolation.isFull(6, 2));
        percolation.open(5, 4);
        StdOut.println("Is (5, 4) full? " + percolation.isFull(5, 4));


        StdOut.println("Percolates after opening? " + percolation.percolates());
        StdOut.println("Is full? " + percolation.isFull(5, 5));

        StdOut.println("Number of open sites: " + percolation.numberOfOpenSites());
        for (int row = 0; row < percolation.n; row++) {
            for (int col = 0; col < percolation.n; col++) {
                StdOut.print(percolation.grid[row][col] + " | ");
            }
            StdOut.println();
        }
    }
}
