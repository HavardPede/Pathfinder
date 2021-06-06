import React, { useState } from 'react';
import { aStar } from '../../api';
import { StatusMatrix, Status, Coordinate, BoardData } from '../../types';
import Square from "../Square/Square"
import "./Board.css"

type Props = { selectedStatus: Status }
type State = { board: StatusMatrix, start: Coordinate | null, end: Coordinate | null }

const BOARD_SIZE: Coordinate = {x: 13, y: 26};
const PATH_DRAWING_SPEED = 100

export default function Board({ selectedStatus }: Props) {
    const [state, setState] = useState<State>(initialState());
    const [path, setPath] = useState<Coordinate[] | null>(null);
    const [isDrawingPath, setIsDrawingPath] = useState<boolean>(false);

    /**
     * Used to create BoardData which will sent to backend for computing of path
     */
    function formatBoardState(): BoardData {
        const { board, start, end } = state
        if (!(start && end)) return "invalidBoard"

        let walls: Coordinate[] = []

        board.forEach((_, x) => {
            board[x].forEach((_, y) => {
                if (board[x][y] === "wall") walls.push({x, y})
            })
        })

        return { size: BOARD_SIZE, walls, start, end }
    }

    /**
     * Updates a square with the new status
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param status The new Status for the coordinate
     * @param boardToUse (optional) In case you want to use a board separate from state. Defaults to the board from state
     * @returns The updated board-object
     */
    function updateSquare(x: number, y: number, status: Status, boardToUse: StatusMatrix | null = null): StatusMatrix {
        let board: StatusMatrix = deepCopyMatrix(boardToUse ?? state.board)
        // Dont allow override of start and finish
        if(['start', 'finish'].includes(board[x][y])) return board;

        board[x][y] = board[x][y] === status ? "empty" : status

        const [board1, start] = handleUniqueValue(board, state.start, {x, y}, status === "start")
        const [board2, end] = handleUniqueValue(board1, state.end, {x, y}, status === "finish")
        setState({ ...state, board: board2, start, end })

        return board2;
    }

    /**
     * Removes all statuses and allows the user to click run again.
     */
    function reset() {
        setState(initialState());
        setPath(null);
    }

    /**
     * Tries to contact backend to compute the path between start and finish
     */
    async function run() {
        const data = formatBoardState();
        if (data === "invalidBoard") {
            console.error('Tried to compute the path with an invalid board...');
            return;
        };

        const response = await aStar(data);
        if (response.status === 'success') drawPath(response.data);
    }

    /**
     * Used to draw the computed path from start to finish with a delay to make for a SMOOOTH viewing experience.
     * @param path The list of coordinates with index 0 pointing to start, and last index pointing to finish.
     */
    async function drawPath(path: Coordinate[]) {
        setIsDrawingPath(true);
        // Remove start- and finish-square from path
        path = path.slice(1, path.length - 1);

        /**
         *  We need to save the board as multiple calls to updateSquare from the same context will point to the same
         *  version of state. This means each consecutive call will not include the changes made in the pervious call.
         *
         *  This is a bit hacky as we essentially detach the state until we complete the rendering of the path.
         */
        let board: StatusMatrix = clearPath();
        setPath(path);

        path.forEach((coord, index) => {
            // Sets a longer delay per iteration so we can visually represent each step
            setTimeout(() => {
                board = updateSquare(coord.x, coord.y, 'path', board);
                if (index === path.length - 1) setIsDrawingPath(false);
            }, PATH_DRAWING_SPEED * index)
        })
    }

    /**
     * Sets all path-squares to empty and returns the new board.
     */
    function clearPath() {
         const board = state.board;
         path?.forEach(square => board[square.x][square.y] = 'empty');

        setState({...state, board});
        setPath(null);

         return board;
    }

    const board = state.board.map((row, x) => {
        return <div className="c-board__row">
            {row.map((status, y) => {
                const onClick = () => { updateSquare(x, y, selectedStatus) }
                return <Square {...{ onClick, status }} key={`${x}_${y}`} />
            })}
        </div>
    })

    return <div className="c-board">
        {board}
        <button onClick={run} className="c-button c-button--primary" disabled={isDrawingPath}>Run Pathfinder</button>
        <button onClick={reset} className="c-button c-button--secondary" disabled={isDrawingPath}>Reset Board</button>
        <h2 hidden={path === null}>Route-length: {path?.length}</h2>
    </div>
}

function initialState(): State {
    let board: StatusMatrix = Array(BOARD_SIZE.x).fill(Array(BOARD_SIZE.y).fill("empty"))
    // Currently all rows point to the same array.
    board = deepCopyMatrix(board);
    const start: Coordinate = {x: 1, y: 1};
    const end: Coordinate = {x: BOARD_SIZE.x - 2, y: BOARD_SIZE.y - 2};

    board[start.x][start.y] = 'start';
    board[end.x][end.y] = 'finish';

    return { board, start, end }
}

/**
 * Used to ensure all rows arent referring to the same array in memory.
 * @param matrix
 */
function deepCopyMatrix(matrix: any[][]): any[][] {
    return JSON.parse(JSON.stringify(matrix))
}

function handleUniqueValue(board: StatusMatrix, coordinate: Coordinate | null, newCoordinate: Coordinate, assigningSameStatus: boolean): [StatusMatrix, Coordinate | null] {
    if (coordinate && compareCoordinate(coordinate, newCoordinate)) return [board, null]

    if (!assigningSameStatus) return [board, coordinate]

    // If we have no previous unqiue value, assign the unique value
    if (coordinate === null) return [board, newCoordinate]

    const {x, y} = coordinate
    board[x][y] = "empty"
    return [board, newCoordinate]
}

function compareCoordinate(coord1: Coordinate, coord2: Coordinate): boolean {
    return JSON.stringify(coord1) === JSON.stringify(coord2)
}
