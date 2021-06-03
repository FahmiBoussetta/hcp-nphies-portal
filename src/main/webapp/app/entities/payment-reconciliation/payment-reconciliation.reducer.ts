import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaymentReconciliation, defaultValue } from 'app/shared/model/payment-reconciliation.model';

export const ACTION_TYPES = {
  FETCH_PAYMENTRECONCILIATION_LIST: 'paymentReconciliation/FETCH_PAYMENTRECONCILIATION_LIST',
  FETCH_PAYMENTRECONCILIATION: 'paymentReconciliation/FETCH_PAYMENTRECONCILIATION',
  CREATE_PAYMENTRECONCILIATION: 'paymentReconciliation/CREATE_PAYMENTRECONCILIATION',
  UPDATE_PAYMENTRECONCILIATION: 'paymentReconciliation/UPDATE_PAYMENTRECONCILIATION',
  PARTIAL_UPDATE_PAYMENTRECONCILIATION: 'paymentReconciliation/PARTIAL_UPDATE_PAYMENTRECONCILIATION',
  DELETE_PAYMENTRECONCILIATION: 'paymentReconciliation/DELETE_PAYMENTRECONCILIATION',
  RESET: 'paymentReconciliation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaymentReconciliation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PaymentReconciliationState = Readonly<typeof initialState>;

// Reducer

export default (state: PaymentReconciliationState = initialState, action): PaymentReconciliationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYMENTRECONCILIATION):
    case REQUEST(ACTION_TYPES.UPDATE_PAYMENTRECONCILIATION):
    case REQUEST(ACTION_TYPES.DELETE_PAYMENTRECONCILIATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTRECONCILIATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION):
    case FAILURE(ACTION_TYPES.CREATE_PAYMENTRECONCILIATION):
    case FAILURE(ACTION_TYPES.UPDATE_PAYMENTRECONCILIATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTRECONCILIATION):
    case FAILURE(ACTION_TYPES.DELETE_PAYMENTRECONCILIATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTRECONCILIATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYMENTRECONCILIATION):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYMENTRECONCILIATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTRECONCILIATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYMENTRECONCILIATION):
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

const apiUrl = 'api/payment-reconciliations';

// Actions

export const getEntities: ICrudGetAllAction<IPaymentReconciliation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYMENTRECONCILIATION_LIST,
  payload: axios.get<IPaymentReconciliation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPaymentReconciliation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYMENTRECONCILIATION,
    payload: axios.get<IPaymentReconciliation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPaymentReconciliation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYMENTRECONCILIATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaymentReconciliation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYMENTRECONCILIATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPaymentReconciliation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAYMENTRECONCILIATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaymentReconciliation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYMENTRECONCILIATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
