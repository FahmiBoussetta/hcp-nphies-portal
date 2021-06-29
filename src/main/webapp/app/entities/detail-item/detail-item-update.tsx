import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './detail-item.reducer';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DetailItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const items = useAppSelector(state => state.item.entities);
  const detailItemEntity = useAppSelector(state => state.detailItem.entity);
  const loading = useAppSelector(state => state.detailItem.loading);
  const updating = useAppSelector(state => state.detailItem.updating);
  const updateSuccess = useAppSelector(state => state.detailItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/detail-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...detailItemEntity,
      ...values,
      item: items.find(it => it.id.toString() === values.itemId.toString()),
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
          ...detailItemEntity,
          itemId: detailItemEntity?.item?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.detailItem.home.createOrEditLabel" data-cy="DetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.detailItem.home.createOrEditLabel">Create or edit a DetailItem</Translate>
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
                  id="detail-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.sequence')}
                id="detail-item-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.tax')}
                id="detail-item-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.transportationSRCA')}
                id="detail-item-transportationSRCA"
                name="transportationSRCA"
                data-cy="transportationSRCA"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.imaging')}
                id="detail-item-imaging"
                name="imaging"
                data-cy="imaging"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.laboratory')}
                id="detail-item-laboratory"
                name="laboratory"
                data-cy="laboratory"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.medicalDevice')}
                id="detail-item-medicalDevice"
                name="medicalDevice"
                data-cy="medicalDevice"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.oralHealthIP')}
                id="detail-item-oralHealthIP"
                name="oralHealthIP"
                data-cy="oralHealthIP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.oralHealthOP')}
                id="detail-item-oralHealthOP"
                name="oralHealthOP"
                data-cy="oralHealthOP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.procedure')}
                id="detail-item-procedure"
                name="procedure"
                data-cy="procedure"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.services')}
                id="detail-item-services"
                name="services"
                data-cy="services"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.medicationCode')}
                id="detail-item-medicationCode"
                name="medicationCode"
                data-cy="medicationCode"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.quantity')}
                id="detail-item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.detailItem.unitPrice')}
                id="detail-item-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="detail-item-item"
                name="itemId"
                data-cy="item"
                label={translate('hcpNphiesPortalApp.detailItem.item')}
                type="select"
              >
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/detail-item" replace color="info">
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

export default DetailItemUpdate;
