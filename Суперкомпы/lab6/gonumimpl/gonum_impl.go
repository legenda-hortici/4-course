package gonumimpl

import (
	"math/rand"
	"sync"

	"gonum.org/v1/gonum/mat"
)

func RandomMatrix(rows, cols int) *mat.Dense {
	data := make([]float64, rows*cols)
	for i := range data {
		data[i] = rand.Float64() * 10
	}
	return mat.NewDense(rows, cols, data)
}

func ParallelMultiply(a, b *mat.Dense, workers int) *mat.Dense {
	ar, ac := a.Dims()
	br, bc := b.Dims()
	if ac != br {
		panic("несовместимые размеры матриц")
	}
	result := mat.NewDense(ar, bc, nil)
	var wg sync.WaitGroup
	chunkSize := ar / workers
	for w := 0; w < workers; w++ {
		start := w * chunkSize
		end := start + chunkSize
		if w == workers-1 {
			end = ar
		}
		wg.Add(1)
		go func(start, end int) {
			defer wg.Done()
			for i := start; i < end; i++ {
				for j := 0; j < bc; j++ {
					var sum float64
					for k := 0; k < ac; k++ {
						sum += a.At(i, k) * b.At(k, j)
					}
					result.Set(i, j, sum)
				}
			}
		}(start, end)
	}
	wg.Wait()
	return result
}
