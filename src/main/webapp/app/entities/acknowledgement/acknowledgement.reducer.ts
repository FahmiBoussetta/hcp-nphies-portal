import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAcknowledgement, defaultValue } from 'app/shared/model/acknowledgement.model';

export const ACTION_TYPES = {
  FETCH_ACKNOWLEDGEMENT_LIST: 'acknowledgement/FETCH_ACKNOWLEDGEMENT_LIST',
  FETCH_ACKNOWLEDGEMENT: 'acknowledgement/FETCH_ACKNOWLEDGEMENT',
  CREATE_ACKNOWLEDGEMENT: 'acknowledgement/CREATE_ACKNOWLEDGEMENT',
  UPDATE_ACKNOWLEDGEMENT: 'acknowledgement/UPDATE_ACKNOWLEDGEMENT',
  PARTIAL_UPDATE_ACKNOWLEDGEMENT: 'acknowledgement/PARTIAL_UPDATE_ACKNOWLEDGEMENT',
  DELETE_ACKNOWLEDGEMENT: 'acknowledgement/DELETE_ACKNOWLEDGEMENT',
  RESET: 'acknowledgement/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAcknowledgement>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AcknowledgementState = Readonly<typeof initialState>;

// Reducer

export default (state: AcknowledgementState = initialState, action): AcknowledgementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACKNOWLEDGEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ACKNOWLEDGEMENT):
    case REQUEST(ACTION_TYPES.DELETE_ACKNOWLEDGEMENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ACKNOWLEDGEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT):
    case FAILURE(ACTION_TYPES.CREATE_ACKNOWLEDGEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ACKNOWLEDGEMENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ACKNOWLEDGEMENT):
    case FAILURE(ACTION_TYPES.DELETE_ACKNOWLEDGEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACKNOWLEDGEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACKNOWLEDGEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ACKNOWLEDGEMENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ACKNOWLEDGEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACKNOWLEDGEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/acknowledgements';

// Actions

export const getEntities: ICrudGetAllAction<IAcknowledgement> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACKNOWLEDGEMENT_LIST,
  payload: axios.get<IAcknowledgement>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAcknowledgement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACKNOWLEDGEMENT,
    payload: axios.get<IAcknowledgement>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAcknowledgement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACKNOWLEDGEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAcknowledgement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACKNOWLEDGEMENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAcknowledgement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ACKNOWLEDGEMENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAcknowledgement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACKNOWLEDGEMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
