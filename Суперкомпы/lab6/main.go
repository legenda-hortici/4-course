package main

import (
	"fmt"
	"math/rand"
	"time"

	"gonum.org/v1/gonum/mat"
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/plotutil"
	"gonum.org/v1/plot/vg"

	"lab6/gonumimpl"
	"lab6/simpleimpl"
)

func plotResults(sizes []int, gonumTimes, simpleTimes []float64) {
	p := plot.New()
	p.Title.Text = "Сравнение производительности"
	p.X.Label.Text = "Размер матрицы"
	p.Y.Label.Text = "Время (мс)"

	gonumPts := make(plotter.XYs, len(sizes))
	simplePts := make(plotter.XYs, len(sizes))
	for i := range sizes {
		gonumPts[i].X = float64(sizes[i])
		gonumPts[i].Y = gonumTimes[i]
		simplePts[i].X = float64(sizes[i])
		simplePts[i].Y = simpleTimes[i]
	}

	line1, _ := plotter.NewLine(gonumPts)
	line1.Color = plotutil.Color(0)
	line1.LineStyle.Width = vg.Points(1.5)

	line2, _ := plotter.NewLine(simplePts)
	line2.Color = plotutil.Color(1)
	line2.LineStyle.Width = vg.Points(1.5)

	p.Add(line1, line2)
	p.Legend.Add("Gonum", line1)
	p.Legend.Add("Simple ([][]float64)", line2)

	if err := p.Save(6*vg.Inch, 4*vg.Inch, "compare.png"); err != nil {
		panic(err)
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	sizes := []int{100, 200, 500, 1000, 2000}
	workers := 4

	var gonumTimes, simpleTimes []float64

	for _, size := range sizes {
		par := func(a, b *mat.Dense) *mat.Dense {
			return gonumimpl.ParallelMultiply(a, b, workers)
		}

		gseq := gonumimpl.RandomMatrix(size, size)
		gpar := gonumimpl.RandomMatrix(size, size)

		start := time.Now()
		_ = par(gseq, gpar)
		dur2 := time.Since(start)

		start = time.Now()
		_ = simpleimpl.Multiply(simpleimpl.RandomMatrix(size, size), simpleimpl.RandomMatrix(size, size))
		dur3 := time.Since(start)

		fmt.Printf("Размер %d: Gonum (паралл.) %.2f мс, Simple %.2f мс\n",
			size, dur2.Seconds()*1000, dur3.Seconds()*1000)

		gonumTimes = append(gonumTimes, dur2.Seconds()*1000)
		simpleTimes = append(simpleTimes, dur3.Seconds()*1000)
	}

	plotResults(sizes, gonumTimes, simpleTimes)
	fmt.Println("График сохранён в compare.png")
}
