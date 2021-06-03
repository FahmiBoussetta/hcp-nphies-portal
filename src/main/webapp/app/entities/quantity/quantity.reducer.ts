import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQuantity, defaultValue } from 'app/shared/model/quantity.model';

export const ACTION_TYPES = {
  FETCH_QUANTITY_LIST: 'quantity/FETCH_QUANTITY_LIST',
  FETCH_QUANTITY: 'quantity/FETCH_QUANTITY',
  CREATE_QUANTITY: 'quantity/CREATE_QUANTITY',
  UPDATE_QUANTITY: 'quantity/UPDATE_QUANTITY',
  PARTIAL_UPDATE_QUANTITY: 'quantity/PARTIAL_UPDATE_QUANTITY',
  DELETE_QUANTITY: 'quantity/DELETE_QUANTITY',
  RESET: 'quantity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQuantity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type QuantityState = Readonly<typeof initialState>;

// Reducer

export default (state: QuantityState = initialState, action): QuantityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_QUANTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUANTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_QUANTITY):
    case REQUEST(ACTION_TYPES.UPDATE_QUANTITY):
    case REQUEST(ACTION_TYPES.DELETE_QUANTITY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_QUANTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_QUANTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUANTITY):
    case FAILURE(ACTION_TYPES.CREATE_QUANTITY):
    case FAILURE(ACTION_TYPES.UPDATE_QUANTITY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_QUANTITY):
    case FAILURE(ACTION_TYPES.DELETE_QUANTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUANTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUANTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUANTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_QUANTITY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_QUANTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUANTITY):
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

const apiUrl = 'api/quantities';

// Actions

export const getEntities: ICrudGetAllAction<IQuantity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_QUANTITY_LIST,
  payload: axios.get<IQuantity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IQuantity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUANTITY,
    payload: axios.get<IQuantity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUANTITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUANTITY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_QUANTITY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQuantity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUANTITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
