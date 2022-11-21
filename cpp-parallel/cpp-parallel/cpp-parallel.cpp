#include <iostream>
#include <omp.h>
#include <math.h>

const int SIZE = 1000;
const int ITERATIONS_AM = 10;

double m1[SIZE][SIZE];
double m2[SIZE][SIZE];
double res[SIZE][SIZE];

using namespace std;

void multiplyMatrices(double m1[][SIZE], double m2[][SIZE]) {
    int row, col, i;
#pragma omp parallel for private(row, col, i) shared(m1,m2,res)
    for (row = 0; row < SIZE; row++) {
        for (col = 0; col < SIZE; col++) {
            for (i = 0; i < SIZE; i++) {
                res[row][col] += m1[row][i] * m2[i][col];
            }
        }
    }
}

double randomDouble(double minVal, double maxVal){
    double f = (double)rand() / RAND_MAX;
    return minVal + f * (maxVal - minVal);
}

int main() {
    omp_set_num_threads(omp_get_num_procs());
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            m1[i][j] = randomDouble(0.0, 1.0);
            m2[i][j] = randomDouble(0.0, 1.0);
        }
    }

    double minTime = INT_MAX;
    double sum = 0;

    for (int i = 0; i < ITERATIONS_AM; i++) {
        auto start = omp_get_wtime();
        multiplyMatrices(m1, m2);
        auto duration = omp_get_wtime() - start;
        sum += duration;
        minTime = min(minTime, duration);
    }

    cout << "Best time:\t" << minTime << "s" << endl;
    cout << "Average time:\t" << sum / ITERATIONS_AM << "s" << endl;
}