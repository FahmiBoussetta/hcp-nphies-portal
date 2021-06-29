import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDetailItem } from 'app/shared/model/detail-item.model';
import { getEntities as getDetailItems } from 'app/entities/detail-item/detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sub-detail-item.reducer';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SubDetailItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const detailItems = useAppSelector(state => state.detailItem.entities);
  const subDetailItemEntity = useAppSelector(state => state.subDetailItem.entity);
  const loading = useAppSelector(state => state.subDetailItem.loading);
  const updating = useAppSelector(state => state.subDetailItem.updating);
  const updateSuccess = useAppSelector(state => state.subDetailItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/sub-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...subDetailItemEntity,
      ...values,
      detailItem: detailItems.find(it => it.id.toString() === values.detailItemId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...subDetailItemEntity,
          detailItemId: subDetailItemEntity?.detailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.subDetailItem.home.createOrEditLabel" data-cy="SubDetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.subDetailItem.home.createOrEditLabel">Create or edit a SubDetailItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="sub-detail-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.sequence')}
                id="sub-detail-item-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.tax')}
                id="sub-detail-item-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.transportationSRCA')}
                id="sub-detail-item-transportationSRCA"
                name="transportationSRCA"
                data-cy="transportationSRCA"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.imaging')}
                id="sub-detail-item-imaging"
                name="imaging"
                data-cy="imaging"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.laboratory')}
                id="sub-detail-item-laboratory"
                name="laboratory"
                data-cy="laboratory"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.medicalDevice')}
                id="sub-detail-item-medicalDevice"
                name="medicalDevice"
                data-cy="medicalDevice"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.oralHealthIP')}
                id="sub-detail-item-oralHealthIP"
                name="oralHealthIP"
                data-cy="oralHealthIP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.oralHealthOP')}
                id="sub-detail-item-oralHealthOP"
                name="oralHealthOP"
                data-cy="oralHealthOP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.procedure')}
                id="sub-detail-item-procedure"
                name="procedure"
                data-cy="procedure"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.services')}
                id="sub-detail-item-services"
                name="services"
                data-cy="services"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.medicationCode')}
                id="sub-detail-item-medicationCode"
                name="medicationCode"
                data-cy="medicationCode"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.quantity')}
                id="sub-detail-item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.subDetailItem.unitPrice')}
                id="sub-detail-item-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="sub-detail-item-detailItem"
                name="detailItemId"
                data-cy="detailItem"
                label={translate('hcpNphiesPortalApp.subDetailItem.detailItem')}
                type="select"
              >
                <option value="" key="0" />
                {detailItems
                  ? detailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sub-detail-item" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SubDetailItemUpdate;
