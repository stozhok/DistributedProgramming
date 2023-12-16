package main

import (
	"fmt"
	"sync"
)

type Graph struct {
	mu         sync.RWMutex
	cityRoutes map[string]map[string]int
}

func InitGraph() *Graph {
	return &Graph{
		cityRoutes: make(map[string]map[string]int),
	}
}

func (g *Graph) AddRoute(cityA, cityB string, price int) {
	g.mu.Lock()
	defer g.mu.Unlock()

	if g.cityRoutes[cityA] == nil {
		g.cityRoutes[cityA] = make(map[string]int)
	}

	g.cityRoutes[cityA][cityB] = price
	g.cityRoutes[cityB][cityA] = price
}

func (g *Graph) RemoveRoute(cityA, cityB string) {
	g.mu.Lock()
	defer g.mu.Unlock()

	delete(g.cityRoutes[cityA], cityB)
	delete(g.cityRoutes[cityB], cityA)
}

func (g *Graph) ChangeTicketPrice(cityA, cityB string, newPrice int) {
	g.mu.Lock()
	defer g.mu.Unlock()

	g.cityRoutes[cityA][cityB] = newPrice
	g.cityRoutes[cityB][cityA] = newPrice
}

func (g *Graph) RemoveCity(city string) {
	g.mu.Lock()
	defer g.mu.Unlock()

	delete(g.cityRoutes, city)

	for _, routes := range g.cityRoutes {
		delete(routes, city)
	}
}

func (g *Graph) AddCity(city string) {
	g.mu.Lock()
	defer g.mu.Unlock()

	g.cityRoutes[city] = make(map[string]int)
}

func (g *Graph) FindPath(cityA, cityB string) (bool, int) {
	g.mu.RLock()
	defer g.mu.RUnlock()

	visited := make(map[string]bool)
	return g.dfs(cityA, cityB, visited)
}

func (g *Graph) dfs(current, destination string, visited map[string]bool) (bool, int) {
	if current == destination {
		return true, 0
	}

	visited[current] = true

	for city, price := range g.cityRoutes[current] {
		if !visited[city] {
			if found, totalCost := g.dfs(city, destination, visited); found {
				return true, totalCost + price
			}
		}
	}

	return false, 0
}

func main() {
	graph := InitGraph()

	graph.AddCity("Kyiv")
	graph.AddCity("Lviv")
	graph.AddCity("Odessa")

	graph.AddRoute("Kyiv", "Lviv", 500)
	graph.AddRoute("Kyiv", "Odessa", 700)

	graph.ChangeTicketPrice("Kyiv", "Lviv", 550)

	graph.RemoveRoute("Kyiv", "Odessa")

	graph.RemoveCity("Odessa")
	graph.AddCity("Kharkiv")

	found, cost := graph.FindPath("Kyiv", "Lviv")
	if found {
		fmt.Printf("The way is found, the price of the trip: %d\n", cost)
	} else {
		fmt.Println("Way don't find")
	}
}
