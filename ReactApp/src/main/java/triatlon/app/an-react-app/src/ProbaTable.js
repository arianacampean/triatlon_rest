import React from "react";
//import './ProbaApp.css'
class ProbaRow extends React.Component{

    handleClicke=(event)=>{
        console.log('delete button pentru '+this.props.proba.id);
        this.props.deleteFunc(this.props.proba.id);
    }

    render() {
        return (
            <tr className="rand">
                <td className="tab">{this.props.proba.id}</td>
                <td className="tab">{this.props.proba.nume}</td>
                <td className="tab">{this.props.proba.id_arb}</td>
                <td><button id="sters" onClick={this.handleClicke}>Delete</button></td>
            </tr>
        );
    }
}


class ProbaTable extends React.Component {
    render() {
        var rows = [];
        var functieStergere=this.props.deleteFunc;
        this.props.probe.forEach(function(proba) {

            rows.push(<ProbaRow proba={proba} key={proba.id} deleteFunc={functieStergere} />);
        });
        return (<div className="ProbaTable">

                <table className="center">
                    <thead>
                    <tr className="numes">
                        <th className="aici">Id-ul</th>
                        <th className="aici">Nume</th>
                        <th className="aici">Id_arb</th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default ProbaTable;