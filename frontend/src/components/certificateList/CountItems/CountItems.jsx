import { React } from 'react';
import { Form } from 'react-bootstrap';
import changeSizeAction from '../../../store/actions/ChangeSizeAction';
import { connect, useDispatch } from 'react-redux';

function CountItems({ size }) {
    const dispatch = useDispatch();

    return (
        <Form.Select aria-label="Default select example" fixed="bottom" style={{ margin: '10px 0 0 0' }}
            defaultValue={size} onChange={(e) => dispatch(changeSizeAction(e.target.value))}>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
        </Form.Select>
    );
}

function mapStateToProps(state) {
    return {
        size: state.size
    }
}

export default connect(mapStateToProps, null)(CountItems);