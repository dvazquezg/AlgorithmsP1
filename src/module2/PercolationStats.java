package module2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation percolation;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) {
            throw new IllegalArgumentException();
        }

        percolation = new Percolation(n);
        double[] percolationThresholds = new double[trials];
        for (int t = 0; t < trials; t++) {
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                percolation.open(row, col);
            }
            percolationThresholds[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        this.mean = StdStats.mean(percolationThresholds);
        this.stddev = StdStats.stddev(percolationThresholds);
        this.confidenceLo = this.mean - (1.96 * this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean + (1.96 * this.stddev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("Mean: " + percolationStats.mean());
        StdOut.println("Stddev: " + percolationStats.stddev());
        StdOut.println("95% confidence interval [" + percolationStats.confidenceLo() +
                ", " + percolationStats.confidenceHi() + "]");
    }
}
