import React from 'react';
import { Status } from '../../types';
import Square from "../Square/Square"
import "./Toolbar.css"

type Props = { setStatus: (status: Status) => void, selectedStatus: Status }

export default function Toolbar(props: Props) {
    return (
        <div className={"c-toolbar"}>
            {renderSquare("start", props)}
            {renderSquare("wall", props)}
            {renderSquare("finish", props)}
        </div>
    );
}

function renderSquare(status: Status, { setStatus, selectedStatus }: Props): JSX.Element {
    return <div>
        <Square onClick={() => setStatus(status)} status={status} />
        {status === selectedStatus ? <b>^</b> : null}
    </div>
}