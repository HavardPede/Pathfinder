import React from 'react';
import { Status } from '../../types';
import "./Square.css"

type Props = {
    onClick(): void
    status: Status
}

export default function Board({ onClick, status }: Props) {
    function mouseEnter(event: any) {
        if ([1, 3].includes(event.buttons)) onClick()
    }

    return <div
        className={`c-square c-square--${status}`}
        onClick={onClick}
        onMouseEnter={mouseEnter}
        draggable="false"
    >
    </div >
}