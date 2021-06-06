import Axios from "axios";
import {BoardData, Coordinate} from "./types";

const A_STAR_URL = "/algorithm/astar"

type ResponseBody = {status: 'success', data: Coordinate[]} | {status: 'error'};

export async function aStar(data: BoardData): Promise<ResponseBody> {
    try {
        const result = await Axios.post(A_STAR_URL, data);
        return {status: 'success', data: result.data};

    } catch (error) {
        console.log("got this error", error);
        return {status: 'error'};
    }
}