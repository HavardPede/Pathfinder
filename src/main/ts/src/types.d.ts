export type Status = "empty" | "start" | "finish" | "wall" | 'path'

export type StatusMatrix = SquareStatus[][]

export type Coordinate = {x: number, y: number}

export type BoardData = {
    size: Coordinate,
    walls: Coordinate[],
    start: Coordinate,
    end: Coordinate
} | "invalidBoard"