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
        int siteNum = (row - 1) * this.n + col;

        // connect to adjacent open-top site (or virtual top site 0)
        if (row - 1 == 0) {
            this.wUnionFind.union(siteNum, 0);
        } else if (this.grid[row - 2][col - 1] == 1) {
            this.wUnionFind.union(siteNum, siteNum - this.n);
        }

        // connect to adjacent open-bottom site (or virtual bottom site n + 1)
        if (row + 1 > this.n) {
            this.wUnionFind.union(siteNum, this.n + 1);
        } else if (this.grid[row][col - 1] == 1) {
            this.wUnionFind.union(siteNum, siteNum + this.n);
        }

        // connect to adjacent open-left site
        if (col - 1 > 0 && this.grid[row - 1][col - 2] == 1) {
            this.wUnionFind.union(siteNum, siteNum - 1);
        }

        // connect to adjacent open-right site
        if (col + 1 < this.n && this.grid[row - 1][col] == 1) {
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
        int siteNum = (row - 1) * this.n + col;

        return this.wUnionFind.find(siteNum) == this.wUnionFind.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // check if virtual sites 0 and n + 1 are connected (have the same parent)
        return this.wUnionFind.find(0) == this.wUnionFind.find(this.n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        StdOut.println("Percolates? " + percolation.percolates());
        StdOut.println("Is full? " + percolation.isFull(2, 2));
        percolation.open(1,2);
        StdOut.println("Percolates after opening (1,2)? " + percolation.percolates());
        StdOut.println("Is open? " + percolation.isOpen(2, 2));
        percolation.open(2,2);
        StdOut.println("Is open? " + percolation.isOpen(2, 2));
        StdOut.println("Percolates after opening (2,2)? " + percolation.percolates());
        StdOut.println("Is full? " + percolation.isFull(2, 2));
        percolation.open(3,2);
        StdOut.println("Percolates after opening (3,2)? " + percolation.percolates());
        StdOut.println("Number of open sites: " + percolation.numberOfOpenSites());
    }
}
