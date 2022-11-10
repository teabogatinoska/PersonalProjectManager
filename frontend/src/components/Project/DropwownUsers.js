import React, { useState } from "react";
import Select from "react-select";

const DropdownUsers = (props) => {
    return (
        <div>
            <components.DropdownUsers {...props}>
                <input
                    type="checkbox"
                    checked={props.isSelected}
                    onChange={() => null}
                />{" "}
                <label>{props.label}</label>
            </components.DropdownUsers>
        </div>
    );
};

export default DropdownUsers;