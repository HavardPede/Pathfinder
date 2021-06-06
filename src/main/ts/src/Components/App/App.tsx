import React, { useState } from 'react';
import Toolbar from '../Toolbar/Toolbar';
import Board from "../Board/Board"
import './App.css';
import { Status } from '../../types';

type State = { selectedStatus: Status }

function App() {
  const [state, updateState] = useState<State>({ selectedStatus: "wall" })

  function setSelectedStatus(selectedStatus: Status) {
    updateState({ selectedStatus })
  }

  return (
    <div className="app">
        <h1>A* Pathfinding algorithm</h1>
        <Toolbar setStatus={setSelectedStatus} selectedStatus={state.selectedStatus} />
        <Board selectedStatus={state.selectedStatus} />
    </div>
  );
}

export default App;
