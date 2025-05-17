package simpleimpl

import (
	"math/rand"
)

func RandomMatrix(rows, cols int) [][]float64 {
	data := make([][]float64, rows)
	for i := range data {
		row := make([]float64, cols)
		for j := range row {
			row[j] = rand.Float64() * 10
		}
		data[i] = row
	}
	return data
}

func Multiply(a, b [][]float64) [][]float64 {
	ar, ac := len(a), len(a[0])
	br, bc := len(b), len(b[0])
	if ac != br {
		panic("несовместимые размеры матриц")
	}
	result := make([][]float64, ar)
	for i := range result {
		result[i] = make([]float64, bc)
		for j := 0; j < bc; j++ {
			for k := 0; k < ac; k++ {
				result[i][j] += a[i][k] * b[k][j]
			}
		}
	}
	return result
}
